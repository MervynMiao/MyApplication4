package com.example.myapplication4

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication4.components.CenteredTitleTopBar
import com.example.myapplication4.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val currentUserId by authViewModel.currentUserId.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var securityAnswer by remember { mutableStateOf("") }
    var isUpdating by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    var isPhoneAvailable by remember { mutableStateOf(true) }
    var isCheckingPhone by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Validation functions
    fun isValidUsername(username: String): Boolean {
        return username.length <= 14 && username.isNotBlank()
    }

    fun isValidMalaysiaPhone(phone: String): Boolean {
        val malaysiaPhonePattern = Pattern.compile(
            "^(\\+?60|0)?1[0-9]{8,9}\$"
        )
        return malaysiaPhonePattern.matcher(phone).matches()
    }

    LaunchedEffect(currentUser) {
        currentUser?.let { user ->
            username = user.name
            phone = user.phone
            email = user.email
            securityAnswer = user.securityAnswer
        }
    }

    // Phone availability check
    LaunchedEffect(phone) {
        if (phone.isNotBlank() && isValidMalaysiaPhone(phone) && phone != currentUser?.phone) {
            isCheckingPhone = true
            authViewModel.checkPhoneAvailability(phone) { available ->
                isPhoneAvailable = available
                isCheckingPhone = false
            }
        } else {
            isPhoneAvailable = true // Reset if phone is empty, invalid, or same as current
        }
    }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                title = "Update Profile",
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFF1ED))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, Color.Black),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFFE8)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.forgetpassword),
                        contentDescription = "Update Profile",
                        modifier = Modifier.size(100.dp)
                    )

                    Text(
                        text = "Update Your Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    // Username
                    OutlinedTextField(
                        value = username,
                        onValueChange = {
                            if (it.length <= 14) username = it
                        },
                        label = { Text("Username (max 14 chars)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        enabled = !isUpdating,
                        isError = username.isNotBlank() && !isValidUsername(username),
                        supportingText = {
                            if (username.isNotBlank() && !isValidUsername(username)) {
                                Text("Maximum 14 characters")
                            }
                        }
                    )

                    // Email (read-only)
                    OutlinedTextField(
                        value = email,
                        onValueChange = {},
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        enabled = false,
                        readOnly = true
                    )

                    // Phone Number
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {
                            phone = it
                            isPhoneAvailable = true // Reset availability when user changes phone
                        },
                        label = { Text("Phone Number (Malaysia)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        enabled = !isUpdating,
                        isError = phone.isNotBlank() && (!isValidMalaysiaPhone(phone) || !isPhoneAvailable),
                        supportingText = {
                            if (phone.isNotBlank()) {
                                if (!isValidMalaysiaPhone(phone)) {
                                    Text("Enter a valid Malaysia phone number")
                                } else if (isCheckingPhone) {
                                    Text("Checking availability...")
                                } else if (!isPhoneAvailable) {
                                    Text("Phone number already exists")
                                } else if (phone == currentUser?.phone) {
                                    Text("Current phone number")
                                } else {
                                    Text("Phone number is available")
                                }
                            }
                        }
                    )

                    // Security Question
                    OutlinedTextField(
                        value = securityAnswer,
                        onValueChange = { securityAnswer = it },
                        label = { Text("What is your favourite animal?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        enabled = !isUpdating
                    )

                    errorMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Red,
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    successMessage?.let { message ->
                        Text(
                            text = message,
                            color = Color.Green,
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = {
                            if (username.isEmpty() || phone.isEmpty() || securityAnswer.isEmpty()) {
                                errorMessage = "Please fill in all fields"
                                return@Button
                            }

                            if (!isValidUsername(username)) {
                                errorMessage = "Username must be 14 characters or less"
                                return@Button
                            }

                            if (!isValidMalaysiaPhone(phone)) {
                                errorMessage = "Please enter a valid Malaysia phone number"
                                return@Button
                            }

                            if (!isPhoneAvailable && phone != currentUser?.phone) {
                                errorMessage = "Phone number already exists"
                                return@Button
                            }

                            isUpdating = true
                            errorMessage = null
                            successMessage = null

                            currentUserId?.let { userId ->
                                authViewModel.updateUserProfile(
                                    userId = userId,
                                    name = username,
                                    phone = phone,
                                    securityAnswer = securityAnswer
                                ) { success, message ->
                                    isUpdating = false
                                    if (success) {
                                        showSuccessDialog = true
                                    } else {
                                        errorMessage = message
                                    }
                                }
                            } ?: run {
                                isUpdating = false
                                errorMessage = "User ID not available"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECEC81)),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !isUpdating &&
                                username.isNotBlank() &&
                                phone.isNotBlank() &&
                                securityAnswer.isNotBlank() &&
                                isValidUsername(username) &&
                                isValidMalaysiaPhone(phone) &&
                                (isPhoneAvailable || phone == currentUser?.phone)
                    ) {
                        if (isUpdating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.Black
                            )
                        } else {
                            Text(
                                "Update Profile",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFDD4C8)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !isUpdating
                    ) {
                        Text(
                            "Cancel",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    if (showSuccessDialog) {
        CustomAlertDialog(
            title = "Profile Updated",
            message = "Your profile has been updated successfully.",
            onDismiss = {
                showSuccessDialog = false
                navController.popBackStack()
            }
        )
    }
}