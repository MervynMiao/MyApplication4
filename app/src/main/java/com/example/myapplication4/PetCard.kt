package com.example.myapplication4

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image

@Composable
fun PetCard(
    date: String,
    time: String,
    service: String,
    petName: String,
    petImageRes: Int,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color(0xFFF4FFCB), shape = RoundedCornerShape(16.dp)) // light green bg
            .border(1.dp, Color.Black, shape = RoundedCornerShape(16.dp))     // outline like your screenshot
            .padding(12.dp)
            .clickable { navController.navigate("myBookingScreen") },
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left side - booking details
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f) // take remaining space
            ) {
                Text(text = date, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = time, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = service, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right side - pet photo & name
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = petImageRes),
                    contentDescription = petName,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(3.dp, Color(0xFFFFC0CB), RoundedCornerShape(12.dp)) // pink border
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = petName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}