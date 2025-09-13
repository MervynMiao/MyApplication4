package com.example.myapplication4.data

data class Pet(
    val id: String = "",
    val name: String = "",
    val breed: String = "",
    val gender: String = "", // "Boy" or "Girl"
    val bod: String = "", // Birthday in format like "2023-01-15"
    val category: String = "", // "Dog" or "Cat"
    val imageUrl: String = "", // Firebase Storage URL for the image
    val userId: String = "" // Add this field to associate with user
)