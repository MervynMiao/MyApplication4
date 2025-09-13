// MyPetScreen.kt
package com.example.myapplication4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication4.components.CenteredTitleTopBar
import com.example.myapplication4.ui.viewmodels.PetViewModel

@Composable
fun MyPetScreen(navController: NavController) {
    val petViewModel: PetViewModel = viewModel()

    // CORRECT WAY: Use collectAsState() to convert StateFlow to composable state
    val pets by petViewModel.pets.collectAsState()
    val isLoading by petViewModel.isLoading.collectAsState()

    // Load pets when screen is first displayed
    LaunchedEffect(Unit) {
        petViewModel.getAllPets()
    }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                title = "My Pets",
                onBackClick = { navController.popBackStack() },
                showBackIcon = true
            )
        },
        containerColor = Color(0xFFFFF1ED) // background color
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(12.dp)
        ) {
            if (isLoading) {
                // Show loading indicator
                Text("Loading pets...", modifier = Modifier.padding(16.dp))
            } else if (pets.isEmpty()) {
                // Show empty state
                Text(
                    text = "No pets found. Add your first pet!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            } else {
                // Scrollable list of pets - NOW THIS WILL WORK
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // take all available space
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pets) { pet ->
                        FurBabyCard(
                            navController = navController,
                            pet = pet // Now 'pet' is correctly recognized as a Pet object
                        )
                    }
                }
            }

            // Fixed button at bottom
            Button(
                onClick = {
                    navController.navigate("add_pet_screen")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD6CB),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Add Pet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}