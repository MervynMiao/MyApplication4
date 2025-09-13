package com.example.myapplication4.repository

import com.example.myapplication4.data.Booking
import kotlin.Result
interface BookingRepository {
    fun addBooking(booking: Booking, callback: (Result<Boolean>) -> Unit)
    fun updateBooking(booking: Booking, callback: (Result<Boolean>) -> Unit)
    fun deleteBooking(bookingId: String, callback: (Result<Boolean>) -> Unit)
    fun getUserBookings(userId: String, callback: (Result<List<Booking>>) -> Unit)
}