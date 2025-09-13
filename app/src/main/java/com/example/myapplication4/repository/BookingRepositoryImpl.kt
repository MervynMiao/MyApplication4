package com.example.myapplication4.repository

import android.os.Handler
import android.os.Looper
import com.example.myapplication4.data.Booking
import com.example.myapplication4.data.BookingDao
import com.google.firebase.firestore.FirebaseFirestore

class BookingRepositoryImpl(
    private val bookingDao: BookingDao,
    private val firestore: FirebaseFirestore
) : BookingRepository {

    private val bookingsCollection = firestore.collection("bookings")
    private val handler = Handler(Looper.getMainLooper())

    override fun addBooking(booking: Booking, callback: (Result<Boolean>) -> Unit) {
        // Add to Firestore first
        bookingsCollection.document(booking.id).set(booking)
            .addOnSuccessListener {
                // Add to Room after successful Firestore operation
                Thread {
                    try {
                        bookingDao.insertBooking(booking)
                        handler.post { callback(Result.success(true)) }
                    } catch (e: Exception) {
                        handler.post { callback(Result.failure(e)) }
                    }
                }.start()
            }
            .addOnFailureListener { e ->
                callback(Result.failure(Exception("Firestore add failed: ${e.message}")))
            }
    }

    override fun updateBooking(booking: Booking, callback: (Result<Boolean>) -> Unit) {
        // Update in Firestore first
        bookingsCollection.document(booking.id).set(booking)
            .addOnSuccessListener {
                // Update in Room after successful Firestore operation
                Thread {
                    try {
                        bookingDao.updateBooking(booking)
                        handler.post { callback(Result.success(true)) }
                    } catch (e: Exception) {
                        handler.post { callback(Result.failure(e)) }
                    }
                }.start()
            }
            .addOnFailureListener { e ->
                callback(Result.failure(Exception("Firestore update failed: ${e.message}")))
            }
    }

    override fun deleteBooking(bookingId: String, callback: (Result<Boolean>) -> Unit) {
        // Delete from Firestore first
        bookingsCollection.document(bookingId).delete()
            .addOnSuccessListener {
                // Delete from Room after successful Firestore operation
                Thread {
                    try {
                        bookingDao.deleteBooking(bookingId)
                        handler.post { callback(Result.success(true)) }
                    } catch (e: Exception) {
                        handler.post { callback(Result.failure(e)) }
                    }
                }.start()
            }
            .addOnFailureListener { e ->
                callback(Result.failure(Exception("Firestore delete failed: ${e.message}")))
            }
    }

    override fun getUserBookings(userId: String, callback: (Result<List<Booking>>) -> Unit) {
        Thread {
            try {
                // Get from Room first
                val localBookings = bookingDao.getUserBookings(userId)

                // Try to sync from Firestore in background
                bookingsCollection
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        val firestoreBookings = snapshot.toObjects(Booking::class.java)
                        // Update Room with latest data in background
                        Thread {
                            try {
                                bookingDao.insertBookings(firestoreBookings)
                            } catch (e: Exception) {
                                println("Room sync failed: ${e.message}")
                            }
                        }.start()
                    }
                    .addOnFailureListener { e ->
                        println("Firestore sync failed: ${e.message}")
                    }

                handler.post { callback(Result.success(localBookings)) }
            } catch (e: Exception) {
                handler.post { callback(Result.failure(e)) }
            }
        }.start()
    }

    companion object {
        fun create(bookingDao: BookingDao): BookingRepositoryImpl {
            return BookingRepositoryImpl(bookingDao, FirebaseFirestore.getInstance())
        }
    }
}