package com.example.myapplication4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookingTitleRow(){
// Whole booking row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Service image
        Image(
            painter = painterResource(id = R.drawable.basicgrooming),
            contentDescription = "Booking Image",
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        // Column with title + date + time
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Title box
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .wrapContentWidth()
                    .background(Color(0xFFFDF2D8), shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Basic Grooming",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    textAlign = TextAlign.Center
                )
            }

            // Date + time row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Date box
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(70.dp)
                        .background(Color(0xFFFDF2D8), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SAT 20/7",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                // Time box
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .wrapContentWidth()
                        .background(Color(0xFFFDF2D8), shape = RoundedCornerShape(12.dp))
                        .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "09:00 to 10:00",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // spacing above & below
        thickness = 8.dp,          // line thickness
        color = Color(0xFFFFD6CB) // light pink line like in your photo
    )
}