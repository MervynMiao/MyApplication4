package com.example.myapplication4.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooking(booking: Booking)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookings(bookings: List<Booking>)

    @Update
    fun updateBooking(booking: Booking)

    @Query("DELETE FROM bookings WHERE id = :bookingId")
    fun deleteBooking(bookingId: String)

    @Query("SELECT * FROM bookings WHERE userId = :userId ORDER BY createdAt DESC")
    fun getUserBookings(userId: String): List<Booking>
}