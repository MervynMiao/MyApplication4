package com.example.myapplication4.repository

import com.example.myapplication4.data.Booking

interface BookingRepository {
    suspend fun addBooking(booking: Booking)
    suspend fun updateBooking(booking: Booking)
    suspend fun deleteBooking(bookingId: String)
    suspend fun getUserBookings(userId: String): List<Booking>
}