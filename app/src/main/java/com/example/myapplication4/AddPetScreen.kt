package com.example.myapplication4

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication4.components.CenteredTitleTopBar
import com.example.myapplication4.ui.viewmodels.PetViewModel
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(navController: NavController, petViewModel: PetViewModel) {
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var bod by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Image picker launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    // Form validation
    val isFormValid = name.isNotBlank() && breed.isNotBlank() &&
            gender.isNotBlank() && bod.isNotBlank() &&
            category.isNotBlank()

    // Date formatter
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // Reset success message after 2 seconds
    if (showSuccessMessage) {
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(2000)
            showSuccessMessage = false
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                title = "Add Pet",
                onBackClick = { navController.popBackStack() },
                showBackIcon = true
            )
        },
        containerColor = Color(0xFFFFF1ED)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Success message
            if (showSuccessMessage) {
                AlertDialog(
                    onDismissRequest = { showSuccessMessage = false },
                    title = { Text("Success") },
                    text = { Text("Pet added successfully!") },
                    confirmButton = {
                        Button(
                            onClick = {
                                showSuccessMessage = false
                                navController.popBackStack()
                            }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }

            // Error message
            if (showError) {
                AlertDialog(
                    onDismissRequest = { showError = false },
                    title = { Text("Error") },
                    text = { Text(errorMessage) },
                    confirmButton = {
                        Button(
                            onClick = { showError = false }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }

            // Photo upload section
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clickable { galleryLauncher.launch("image/*") }
                        .background(Color(0xFFFFD6CB), CircleShape)
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Selected Pet Photo",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.peticon),
                                contentDescription = "Pet Photo",
                                modifier = Modifier.size(60.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Add Photo",
                                modifier = Modifier.size(20.dp),
                                tint = Color.Black
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (selectedImageUri != null) "Photo selected" else "Tap to add photo",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = breed,
                onValueChange = { breed = it },
                label = { Text("Breed") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Gender selection with radio buttons
            Text("Gender", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = gender == "Boy",
                            onClick = { gender = "Boy" }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "Boy",
                        onClick = { gender = "Boy" }
                    )
                    Text("Boy", modifier = Modifier.padding(start = 8.dp))
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = gender == "Girl",
                            onClick = { gender = "Girl" }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "Girl",
                        onClick = { gender = "Girl" }
                    )
                    Text("Girl", modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Date of birth with manual input
            OutlinedTextField(
                value = bod,
                onValueChange = { newValue ->
                    // Simple validation for date format (YYYY-MM-DD)
                    if (newValue.matches(Regex("^\\d{0,4}-?\\d{0,2}-?\\d{0,2}\$")) || newValue.isEmpty()) {
                        bod = newValue
                    }
                },
                label = { Text("Birthday (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text("e.g., 2023-01-15") },
                trailingIcon = {
                    IconButton(onClick = {
                        showDatePicker = true
                    }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Select Date")
                    }
                }
            )

            // Date format info dialog
            if (showDatePicker) {
                AlertDialog(
                    onDismissRequest = { showDatePicker = false },
                    title = { Text("Enter Date") },
                    text = { Text("Please enter the date in YYYY-MM-DD format (e.g., 2023-01-15)") },
                    confirmButton = {
                        Button(
                            onClick = { showDatePicker = false }
                        ) {
                            Text("OK")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Category selection with radio buttons
            Text("Category", style = MaterialTheme.typography.bodyMedium)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = category == "Dog",
                            onClick = { category = "Dog" }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = category == "Dog",
                        onClick = { category = "Dog" }
                    )
                    Text("Dog", modifier = Modifier.padding(start = 8.dp))
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = category == "Cat",
                            onClick = { category = "Cat" }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = category == "Cat",
                        onClick = { category = "Cat" }
                    )
                    Text("Cat", modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Validate date format before submitting
                    val isValidDate = try {
                        if (bod.isNotBlank()) {
                            formatter.parse(bod) // This will throw if format is invalid
                            true
                        } else {
                            true
                        }
                    } catch (e: Exception) {
                        false
                    }

                    if (isValidDate) {
                        petViewModel.addPet(
                            name = name,
                            breed = breed,
                            gender = gender,
                            bod = bod,
                            category = category,
                            imageUri = selectedImageUri, // Pass the selected image
                            onResult = { success, message ->
                                if (success) {
                                    // Show success message
                                    showSuccessMessage = true
                                } else {
                                    // Show error message
                                    errorMessage = message ?: "Failed to add pet"
                                    showError = true
                                }
                            }
                        )
                    } else {
                        // Show date format error
                        errorMessage = "Please enter a valid date in YYYY-MM-DD format"
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD6CB),
                    contentColor = Color.Black
                ),
                enabled = isFormValid
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