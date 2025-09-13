package com.example.myapplication4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication4.components.CenteredTitleTopBar
import com.example.myapplication4.components.ProgressBar

@Composable
fun BookingFlow3Screen(navController: NavController) {
    var currentStep by remember { mutableStateOf(2) }
    val totalSteps = 3  // in your screenshot it shows step 2 → 3

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                "Choose Booking Time",
                onBackClick = { navController.navigate("forgot") }
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF1ED)) // background same as screenshot
                .padding(innerPadding)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step progress bar
            ProgressBar(currentStep, totalSteps)

            Spacer(modifier = Modifier.height(16.dp))

            BookingTitleRow()


        }
    }
}