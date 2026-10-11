
package com.example.powerwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.powerwatch.data.local.PowerWatchDatabase
import com.example.powerwatch.data.repository.ReportRepository
import com.example.powerwatch.screens.DashboardScreen
import com.example.powerwatch.screens.WelcomeScreen
import com.example.powerwatch.ui.theme.PowerWatchTheme
import com.example.powerwatch.viewmodel.ReportViewModel
import com.example.powerwatch.viewmodel.ReportViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = PowerWatchDatabase.getDatabase(applicationContext)
        val repository = ReportRepository(database.reportDao())

        val reportViewModel = ViewModelProvider(
            this,
            ReportViewModelFactory(repository)
        )[ReportViewModel::class.java]

        setContent {
            PowerWatchTheme {
                var showDashboard by remember {
                    mutableStateOf(false)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    if (showDashboard) {
                        DashboardScreen(
                            modifier = Modifier.padding(innerPadding),
                            reportViewModel = reportViewModel
                        )
                    } else {
                        WelcomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onGetStarted = {
                                showDashboard = true
                            }
                        )
                    }
                }
            }
        }
    }
}
