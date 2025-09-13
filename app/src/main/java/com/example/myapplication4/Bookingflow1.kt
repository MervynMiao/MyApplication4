package com.example.myapplication4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication4.components.CenteredTitleTopBar
import com.example.myapplication4.components.PackageDetailsBox
import com.example.myapplication4.components.ProgressBar
import com.example.myapplication4.data.Service
import com.example.myapplication4.ui.viewmodels.BookingViewModel

@Composable
fun BookingFlow1Screen(
    navController: NavController,
    viewModel: BookingViewModel,
    serviceId: String?
) {
    val service by viewModel.selectedService.collectAsState()
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 5

    LaunchedEffect(serviceId) {
        if (serviceId != null) {
            val selectedService = getServiceById(serviceId)
            viewModel.setSelectedService(selectedService)
        }
    }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                service?.name ?: "Service Details",
                onBackClick = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(Color(0xFFFFF1ED))
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ProgressBar(currentStep, totalSteps)

            // Column below the progress bar
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.basicgrooming),
                    contentDescription = "Booking Image",
                    modifier = Modifier.size(200.dp)
                )
            }

            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(60.dp)
                    .background(Color(0xFFFFD6CB), shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                    .clickable { /* Show package details */ },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = "Package Detail",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(350.dp)
                    .height(400.dp)
                    .background(Color(0xFFFFE9D4), shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Package details based on service
                    PackageDetailsBox
                        step = 1,
                        price = getPriceForService(service?.name ?: ""),
                        breedType = "All breed",
                        details = service?.details ?: "Service details not available"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("bookingFlow2") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFDD4C8),
                    contentColor = Color.Black
                )
            ) {
                Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
private fun getPriceForService(serviceName: String): Double {
    return when (serviceName) {
        "Basic Grooming" -> 45.0
        "Deluxe Grooming" -> 70.0
        "Premium Grooming" -> 120.0
        else -> 0.0
    }
}

private fun getServiceById(serviceId: String): Service {
    return when (serviceId) {
        "basic_grooming" -> Service(
            id = "basic_grooming",
            name = "Basic Grooming",
            description = "Starting from RM45+",
            priceRange = "RM45+",
            imageRes = R.drawable.peticon,
            details = "For Small, Medium & Large Breeds"
        )
        "deluxe_grooming" -> Service(
            id = "deluxe_grooming",
            name = "Deluxe Grooming",
            description = "Starting from RM70+",
            priceRange = "RM70+",
            imageRes = R.drawable.peticon,
            details = "Includes Styling & Nail Trimming"
        )
        "premium_grooming" -> Service(
            id = "premium_grooming",
            name = "Premium Grooming",
            description = "Starting from RM120+",
            priceRange = "RM120+",
            imageRes = R.drawable.peticon,
            details = "Full Service with Spa Treatment"
        )
        else -> Service( // Default empty service for safety
            id = "unknown",
            name = "Unknown Service",
            description = "Service not found",
            priceRange = "RM0",
            imageRes = R.drawable.peticon,
            details = "Service details not available"
        )
    }
}