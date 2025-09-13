package com.example.myapplication4

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication4.data.Pet

@Composable
fun FurBabyCard(
    navController: NavController,
    pet: Pet
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color.Black),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1ED)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar (merged with card)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFD6CB)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "「 Fur Baby ID Card 」",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Main row with image + details
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Use a default image if no image URL is provided
                if (pet.imageUrl.isNotEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(pet.imageUrl),
                        contentDescription = "Pet Photo",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color(0xFFFFD6CB), shape = RoundedCornerShape(12.dp))
                    )
                } else {
                    // Fallback to a default pet icon
                    Image(
                        painter = painterResource(id = R.drawable.peticon),
                        contentDescription = "Default Pet Photo",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color(0xFFFFD6CB), shape = RoundedCornerShape(12.dp))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Name : ${pet.name}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text("Breed : ${pet.breed}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text("Gender : ${pet.gender}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text("BOD : ${pet.bod}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text("Category : ${pet.category}", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        navController.navigate("edit_pet_screen/${pet.id}")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFDD4C8),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Edit", fontWeight = FontWeight.Bold)
                }
            }

            // Bottom bar (merged with card)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFD6CB)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A puppy will always love you ♡",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}