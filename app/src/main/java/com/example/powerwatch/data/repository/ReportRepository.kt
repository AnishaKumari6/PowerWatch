
package com.example.powerwatch.data.repository

import com.example.powerwatch.data.local.ReportDao
import com.example.powerwatch.data.local.ReportEntity
import kotlinx.coroutines.flow.Flow

class ReportRepository(
    private val reportDao: ReportDao
) {

    fun getAllReports(): Flow<List<ReportEntity>> {
        return reportDao.getAllReports()
    }

    suspend fun insertReport(report: ReportEntity) {
        reportDao.insertReport(report)
    }

    suspend fun deleteReport(reportId: String) {
        reportDao.deleteReportById(reportId)
    }

    suspend fun deleteAllReports() {
        reportDao.deleteAllReports()
    }
}
