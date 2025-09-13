package com.example.myapplication4.data

import kotlinx.serialization.Serializable
@Serializable // Add this annotation
data class Service(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val priceRange: String = "",
    val imageRes: Int = 0,
    val details: String = "",
    val price: Double = 0.0 // Add this field
)