package com.example.myapplication4.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

// List all your entities (tables) in the `entities` array.
// Increment the version number if you change the database schema.
@Database(entities = [Booking::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "booking_database" // Name of the database file
                ).build().also { Instance = it }
            }
        }
    }
}