package com.example.myapplication4.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// In PackageDetails.kt - fix the parameter names
@Composable
fun PackageDetailsBox(
    step: Int, // Changed from duration
    price: Double,
    breedType: String, // Changed from breed
    details: String
){
    Box(
        modifier = Modifier
            .width(320.dp)
            .background(Color(0xFFFBFFE8), shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Step: $step", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text("Price: RM $price", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text("Breed: $breedType", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }

    Box(
        modifier = Modifier
            .width(320.dp)
            .background(Color(0xFFFBFFE8), shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Package Include: ", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(details, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}