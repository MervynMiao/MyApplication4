// Booking.kt
package com.example.myapplication4.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.Exclude

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey
    val id: String = "",
    val serviceId: String = "",
    val serviceName: String = "",
    val petId: String = "",
    val petName: String = "",
    val userId: String = "",
    val date: String = "", // Format: "YYYY-MM-DD"
    val timeSlot: String = "", // Format: "09:00 - 10:00"
    val status: String = "Pending", // Pending, Confirmed, Completed, Cancelled
    val price: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis(),

    @Exclude
    val service: Service? = null,

    @Exclude
    val pet: Pet? = null
)