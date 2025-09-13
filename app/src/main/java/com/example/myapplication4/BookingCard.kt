package com.example.myapplication4

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication4.data.Service

@Composable
fun BookingCard(
    navController: NavController,
    photo: Int,
    title: String,
    details: String,
    service: Service // Add service parameter
    onBookClick: () -> Unit // Add this parameter
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(5.dp, Color(0xFFFFD6CB), shape = RoundedCornerShape(12.dp))
            .background(Color(0xFFFFF1ED), shape = RoundedCornerShape(12.dp))
            .clickable {
                // Navigate with service data
                navController.navigate("bookingFlow1/${service.id}")
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Left: Image
            Image(
                painter = painterResource(id = photo),
                contentDescription = "Service Icon",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Right side: Column for card + button
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Independent Card for title + details
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(), // auto size
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Color(0xFFFFD6CB)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1ED)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = details,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Button under the card
                Button(
                    onClick = {
                        onBookClick() // Call the callback
                        // Navigate with service data
                        navController.navigate("bookingFlow1/${service.id}")
                    },
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
                    Text(
                        text = "Book",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
