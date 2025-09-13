// AddBookingScreen.kt (updated)
package com.example.myapplication4

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication4.BookingCard
import com.example.myapplication4.data.Service
import com.example.myapplication4.components.CenteredTitleTopBar

@Composable
fun AddBookingScreen(navController: NavController) {
    // Define your services
    val services = listOf(
        Service(
            id = "basic_grooming",
            name = "Basic Grooming",
            description = "Starting from RM45+",
            priceRange = "RM45+",
            imageRes = R.drawable.peticon,
            details = "For Small, Medium & Large Breeds"
        ),
        Service(
            id = "deluxe_grooming",
            name = "Deluxe Grooming",
            description = "Starting from RM70+",
            priceRange = "RM70+",
            imageRes = R.drawable.peticon,
            details = "Includes Styling & Nail Trimming"
        ),
        Service(
            id = "premium_grooming",
            name = "Premium Grooming",
            description = "Starting from RM120+",
            priceRange = "RM120+",
            imageRes = R.drawable.peticon,
            details = "Full Service with Spa Treatment"
        )
    )

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                "Add Booking",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFF1ED))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Scrollable BookingCard set
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                services.forEach { service ->
                    BookingCard(
                        navController = navController,
                        photo = service.imageRes,
                        title = service.name,
                        details = service.description,
                        service = service,
                        onBookClick = {
                            // Create a booking when user clicks "Book"
                            val booking = Booking(
                                serviceId = service.id,
                                serviceName = service.name,
                                price = service.price ?: 0.0,
                                userId = "current_user_id" // Replace with actual user ID
                            )
                            // Save to LocalBase
                            bookingRepository.addBooking(booking)
                        }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Button at bottom
            Button(
                onClick = { navController.navigate("myBookings") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDD4C8),
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.paybutton),
                    contentDescription = "Pay Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "View My Bookings",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}