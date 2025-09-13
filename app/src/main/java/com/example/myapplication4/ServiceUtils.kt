//package com.example.myapplication4
//
//import com.example.myapplication4.data.Service
//
//fun getServiceById(serviceId: String): Service {
//    return when (serviceId) {
//        "basic_grooming" -> Service(
//            id = "basic_grooming",
//            name = "Basic Grooming",
//            description = "Starting from RM45+",
//            priceRange = "RM45+",
//            imageRes = R.drawable.peticon,
//            details = "For Small, Medium & Large Breeds"
//        )
//        "deluxe_grooming" -> Service(
//            id = "deluxe_grooming",
//            name = "Deluxe Grooming",
//            description = "Starting from RM70+",
//            priceRange = "RM70+",
//            imageRes = R.drawable.peticon,
//            details = "Includes Styling & Nail Trimming"
//        )
//        "premium_grooming" -> Service(
//            id = "premium_grooming",
//            name = "Premium Grooming",
//            description = "Starting from RM120+",
//            priceRange = "RM120+",
//            imageRes = R.drawable.peticon,
//            details = "Full Service with Spa Treatment"
//        )
//        else -> Service() // Default empty service
//    }
//}