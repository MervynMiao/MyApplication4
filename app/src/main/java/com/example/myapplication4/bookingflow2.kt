// bookingflow2.kt (updated)
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.myapplication4.components.ProgressBar
import com.example.myapplication4.ui.viewmodels.BookingViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun BookingFlow2Screen(
    navController: NavController,
    viewModel: BookingViewModel
) {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val selectedTime by viewModel.selectedTime.collectAsState()
    var currentStep by remember { mutableStateOf(2) }
    val totalSteps = 3

    // Generate next 5 days
    val next5Days = remember { generateNext5Days() }
    var selectedDay by remember { mutableStateOf(next5Days.first()) }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                "Choose Booking Time",
                onBackClick = { navController.navigateUp() }
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFF1ED))
                .padding(innerPadding)
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Step progress bar
            ProgressBar(currentStep, totalSteps)

            Spacer(modifier = Modifier.height(16.dp))

            BookingTitleRow()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color(0xFFFDF2D8), shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Text on the left
                    Text(
                        text = "Booking Time",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // Image on the right
                    Image(
                        painter = painterResource(id = R.drawable.basicgrooming),
                        contentDescription = "Cute pets",
                        modifier = Modifier.size(150.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main content: Date picker + Time slots
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFE9D4), shape = RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left side: Date Picker (5 days)
                Column(
                    modifier = Modifier.width(100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    next5Days.forEach { dayInfo ->
                        DatePickerButton(
                            dayInfo = dayInfo,
                            isSelected = dayInfo == selectedDay,
                            onDateSelected = { selectedDay = dayInfo }
                        )
                    }
                }

                // Right side: Time slots for selected day (2x4 grid)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Time Slots for ${selectedDay.day} ${selectedDay.date}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 2x4 grid of time slots
                    TimeSlotGrid(
                        selectedTime = selectedTime,
                        onTimeSelected = { time ->
                            viewModel.setSelectedDate("${selectedDay.day} ${selectedDay.date}")
                            viewModel.setSelectedTime(time)
                        }
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                thickness = 8.dp,
                color = Color(0xFFFFD6CB)
            )

            Button(
                onClick = {
                    if (selectedDate != null && selectedTime != null) {
                        navController.navigate("bookingFlow3")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                enabled = selectedDate != null && selectedTime != null,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD6CB),
                    contentColor = Color.Black
                )
            ) {
                Text("Continue", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// Date Picker Button Composable
@Composable
fun DatePickerButton(
    dayInfo: DayInfo,
    isSelected: Boolean,
    onDateSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSelected) Color(0xFFFF8A65) else Color(0xFFFFD6CB),
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .clickable { onDateSelected() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                dayInfo.day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black
            )
            Text(
                dayInfo.date,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

// Time Slot Grid Composable (2x4 layout)
@Composable
fun TimeSlotGrid(
    selectedTime: String?,
    onTimeSelected: (String) -> Unit
) {
    val timeSlots = listOf(
        "09:00 - 10:00",
        "10:00 - 11:00",
        "11:00 - 12:00",
        "12:00 - 13:00",
        "14:00 - 15:00",
        "15:00 - 16:00",
        "16:00 - 17:00",
        "17:00 - 18:00"
    )

    // Create 4 rows with 2 time slots each
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        timeSlots.chunked(2).forEach { rowSlots ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowSlots.forEach { time ->
                    Box(
                        modifier = Modifier
                            .weight(1f) // This works here because we're inside a Row
                            .background(
                                if (time == selectedTime) Color(0xFFFF8A65) else Color.White,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                            .clickable { onTimeSelected(time) }
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                time,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (time == selectedTime) Color.White else Color.Black
                            )
                            Text(
                                "3 left",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (time == selectedTime) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

// Individual Time Slot Button
// Individual Time Slot Button
@Composable
fun TimeSlotButton(
    time: String,
    isSelected: Boolean,
    onTimeSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (isSelected) Color(0xFFFF8A65) else Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .clickable { onTimeSelected() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                time,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black
            )
            Text(
                "3 left",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else Color.Black
            )
        }
    }
}

// Update DayInfo to include full date if needed
data class DayInfo(val day: String, val date: String, val fullDate: String = "")

// Update generateNext5Days to include full date
private fun generateNext5Days(): List<DayInfo> {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
    val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
    val fullDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val days = mutableListOf<DayInfo>()

    for (i in 1..5) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val day = dayFormat.format(calendar.time).uppercase()
        val date = dateFormat.format(calendar.time)
        val fullDate = fullDateFormat.format(calendar.time)
        days.add(DayInfo(day, date, fullDate))
    }

    return days
}