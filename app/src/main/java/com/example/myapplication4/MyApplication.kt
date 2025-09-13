package com.example.myapplication4

import android.app.Application
import com.epicarchitect.localbase.LocalBase
import com.example.myapplication4.data.SimpleBookingRepository

class MyApplication : Application() {

    companion object {
        private lateinit var instance: MyApplication
        fun getInstance(): MyApplication = instance
    }

    private lateinit var bookingRepository: SimpleBookingRepository

    override fun onCreate() {
        super.onCreate()
        instance = this

//        val localBase = LocalBase(
//            context = this,
//            databaseName = "my_pet_app_db"
//        )

        bookingRepository = SimpleBookingRepository()
    }

    fun getBookingRepository(): SimpleBookingRepository{
        return bookingRepository
    }
}