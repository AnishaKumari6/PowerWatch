
package com.example.powerwatch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey
    val id: String,

    val building: String,
    val room: String,
    val equipment: String,
    val description: String,

    // URI of the selected photo, if available
    val photoUri: String? = null,

    // Time the report was created, in milliseconds
    val createdAt: Long = System.currentTimeMillis(),

    // Initial status of a newly submitted report
    val status: String = "Pending"
)
