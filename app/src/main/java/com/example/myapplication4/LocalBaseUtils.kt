package com.example.myapplication4

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication4.data.BookingRepository

@Composable
fun rememberBookingRepository(): BookingRepository {
    val context = LocalContext.current
    return (context.applicationContext as MyApplication).getBookingRepository()
}