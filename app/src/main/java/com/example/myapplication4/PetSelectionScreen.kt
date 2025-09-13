package com.example.myapplication4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapplication4.ui.viewmodels.AuthViewModel
import com.example.myapplication4.ui.viewmodels.BookingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel

// PetSelectionScreen.kt
@Composable
fun PetSelectionScreen(
    navController: NavController,
    viewModel: BookingViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val user by authViewModel.currentUser.collectAsState()
    val selectedService by viewModel.selectedService.collectAsState()
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTime by viewModel.selectedTime.collectAsState()

    var petName by remember { mutableStateOf("") }

    Scaffold(
        topBar = { /* Your top bar */ }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pet selection UI
            TextField(
                value = petName,
                onValueChange = { petName = it },
                label = { Text("Pet Name") }
            )

            Button(
                onClick = {
                    if (petName.isNotBlank() && user != null && selectedService != null) {
                        viewModel.createBooking(
                            userId = user!!.id,
                            petName = petName,
                            service = selectedService!!,
                            date = selectedDate!!,
                            time = selectedTime!!,
                            price = getPriceForService(selectedService!!.name) // Implement this
                        )
                        navController.navigate("bookingConfirmation")
                    }
                },
                enabled = petName.isNotBlank()
            ) {
                Text("Confirm Booking")
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