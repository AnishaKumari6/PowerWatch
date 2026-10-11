
package com.example.powerwatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.powerwatch.data.local.ReportEntity
import com.example.powerwatch.data.repository.ReportRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class ReportsUiState(
    val reports: List<ReportEntity> = emptyList(),
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)

class ReportViewModel(
    private val repository: ReportRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        observeReports()
    }

    private fun observeReports() {
        viewModelScope.launch {
            repository.getAllReports()
                .onStart {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true,
                        errorMessage = null
                    )
                }
                .catch { error ->
                    if (error is CancellationException) throw error

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message
                            ?: "Unable to load reports."
                    )
                }
                .collect { reports ->
                    _uiState.value = ReportsUiState(
                        reports = reports,
                        isLoading = false
                    )
                }
        }
    }


    fun addReport(
        report: ReportEntity,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isSaving = true,
                errorMessage = null
            )

            try {
                repository.insertReport(report)

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = null
                )

                onResult(true, null)
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                val message = error.message ?: "Unable to save the report."

                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    errorMessage = message
                )

                onResult(false, message)
            }
        }
    }


    fun deleteReport(reportId: String) {
        viewModelScope.launch {
            try {
                repository.deleteReport(reportId)
            } catch (error: CancellationException) {
                throw error
            } catch (error: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = error.message
                        ?: "Unable to delete the report."
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null
        )
    }
}
