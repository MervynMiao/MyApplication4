package com.example.myapplication4.login

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication4.CustomAlertDialog
import com.example.myapplication4.R
import com.example.myapplication4.components.CenteredTitleTopBar
import com.example.myapplication4.ui.viewmodels.AuthState
import com.example.myapplication4.ui.viewmodels.AuthViewModel
import kotlinx.coroutines.delay
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    onSignUp: (String, String, String, String, String) -> Unit,
    authState: AuthState,
    isLoading: Boolean
) {
    val authViewModel: AuthViewModel = viewModel() // Add this line to get the ViewModel

    var username by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var securityAnswer by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isPhoneAvailable by remember { mutableStateOf(true) }
    var isEmailAvailable by remember { mutableStateOf(true) } // Added email availability state
    var isCheckingPhone by remember { mutableStateOf(false) }
    var isCheckingEmail by remember { mutableStateOf(false) } // Added email checking state

    var showSuccessDialog by remember { mutableStateOf(false) }

    // Validation functions
    fun isValidUsername(username: String): Boolean {
        return username.length <= 14 && username.isNotBlank()
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        val hasLetter = Pattern.compile("[a-zA-Z]").matcher(password).find()
        val hasDigit = Pattern.compile("[0-9]").matcher(password).find()
        return hasLetter && hasDigit
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        )
        return emailPattern.matcher(email).matches()
    }

    fun isValidMalaysiaPhone(phone: String): Boolean {
        // Malaysian phone numbers can start with +60, 60, 0, or without prefix
        val malaysiaPhonePattern = Pattern.compile(
            "^(\\+?60|0)?1[0-9]{8,9}\$"
        )
        return malaysiaPhonePattern.matcher(phone).matches()
    }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                errorMessage = null
                showSuccessDialog = true
            }
            is AuthState.Error -> {
                errorMessage = authState.message
                showSuccessDialog = false
            }
            else -> {}
        }
    }

    LaunchedEffect(phone) {
        if (phone.isNotBlank() && isValidMalaysiaPhone(phone)) {
            isCheckingPhone = true
            delay(500) // Wait for 500ms after user stops typing
            authViewModel.checkPhoneAvailability(phone) { available ->
                isPhoneAvailable = available
                isCheckingPhone = false
            }
        } else {
            isPhoneAvailable = true // Reset if phone is empty or invalid
        }
    }

    // Added email availability check
    LaunchedEffect(email) {
        if (email.isNotBlank() && isValidEmail(email)) {
            isCheckingEmail = true
            delay(500) // Wait for 500ms after user stops typing
            authViewModel.checkEmailAvailability(email) { available ->
                isEmailAvailable = available
                isCheckingEmail = false
            }
        } else {
            isEmailAvailable = true // Reset if email is empty or invalid
        }
    }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                title = "Sign Up",
                onBackClick = { navController.popBackStack() }
                // showBackIcon defaults to true
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFF1ED))
                .verticalScroll(rememberScrollState()) // Make the column scrollable
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFFE8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = "Create your account to enjoy our pet services. We'll take care of your furry friends with love and professionalism.",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                // Sign Up Box - Removed fixed height to allow content to determine size
                Card(
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(2.dp, Color.Black),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFFE8)),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.forgetpassword),
                            contentDescription = "Sign Up",
                            modifier = Modifier.size(100.dp) // Reduced size
                        )

                        Text(
                            text = "Create Account",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
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
                                .padding(bottom = 8.dp), // Reduced padding
                            enabled = !isLoading,
                            isError = username.isNotBlank() && !isValidUsername(username),
                            supportingText = {
                                if (username.isNotBlank() && !isValidUsername(username)) {
                                    Text("Maximum 14 characters")
                                }
                            }
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
                                .padding(bottom = 8.dp),
                            enabled = !isLoading,
                            isError = phone.isNotBlank() && (!isValidMalaysiaPhone(phone) || !isPhoneAvailable),
                            supportingText = {
                                if (phone.isNotBlank()) {
                                    if (!isValidMalaysiaPhone(phone)) {
                                        Text("Enter a valid Malaysia phone number")
                                    } else if (isCheckingPhone) {
                                        Text("Checking availability...")
                                    } else if (!isPhoneAvailable) {
                                        Text("Phone number already exists")
                                    } else {
                                        Text("Phone number is available")
                                    }
                                }
                            }
                        )

                        // Email
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                email = it
                                isEmailAvailable = true // Reset availability when user changes email
                            },
                            label = { Text("Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp), // Reduced padding
                            enabled = !isLoading,
                            isError = email.isNotBlank() && (!isValidEmail(email) || !isEmailAvailable),
                            supportingText = {
                                if (email.isNotBlank()) {
                                    if (!isValidEmail(email)) {
                                        Text("Enter a valid email address")
                                    } else if (isCheckingEmail) {
                                        Text("Checking availability...")
                                    } else if (!isEmailAvailable) {
                                        Text("Email already exists")
                                    } else {
                                        Text("Email is available")
                                    }
                                }
                            }
                        )

                        // Password
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            enabled = !isLoading,
                            isError = password.isNotBlank() && !isValidPassword(password),
                            supportingText = {
                                if (password.isNotBlank() && !isValidPassword(password)) {
                                    Text("Must be at least 8 characters with letters and numbers")
                                }
                            }
                        )

                        // Confirm Password
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Re-type Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp), // Reduced padding
                            enabled = !isLoading,
                            isError = confirmPassword.isNotBlank() && password != confirmPassword,
                            supportingText = {
                                if (confirmPassword.isNotBlank() && password != confirmPassword) {
                                    Text("Passwords do not match")
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
                                .padding(bottom = 12.dp), // Reduced padding
                            enabled = !isLoading
                        )

                        // Show error message if any
                        errorMessage?.let { message ->
                            Text(
                                text = message,
                                color = Color.Red,
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Center
                            )
                        }

                        Button(
                            onClick = {
                                // Validate all fields
                                if (username.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                                    password.isEmpty() || confirmPassword.isEmpty() || securityAnswer.isEmpty()) {
                                    errorMessage = "Please fill in all fields"
                                    return@Button
                                }

                                if (!isValidUsername(username)) {
                                    errorMessage = "Username must be 14 characters or less"
                                    return@Button
                                }

                                if (!isValidEmail(email)) {
                                    errorMessage = "Please enter a valid email address"
                                    return@Button
                                }

                                if (!isEmailAvailable) {
                                    errorMessage = "Email already exists"
                                    return@Button
                                }

                                if (!isValidMalaysiaPhone(phone)) {
                                    errorMessage = "Please enter a valid Malaysia phone number"
                                    return@Button
                                }

                                if (!isPhoneAvailable) {
                                    errorMessage = "Phone number already exists"
                                    return@Button
                                }

                                if (!isValidPassword(password)) {
                                    errorMessage = "Password must be at least 8 characters with both letters and numbers"
                                    return@Button
                                }

                                if (password != confirmPassword) {
                                    errorMessage = "Passwords do not match"
                                    return@Button
                                }

                                errorMessage = null
                                // Call the onSignUp callback with all required parameters
                                onSignUp(username, email, password, phone, securityAnswer)
                            },
                            enabled = !isLoading && isPhoneAvailable && isEmailAvailable, // Disable button if phone or email is not available
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECEC81)),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.Black
                                )
                            } else {
                                Text(
                                    "Sign Up",
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
                            enabled = !isLoading
                        ) {
                            Text(
                                "Back to Login",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }

    if (showSuccessDialog) {
        CustomAlertDialog(
            title = "Sign Up Successful",
            message = "Your account has been created successfully.",
            onDismiss = {
                showSuccessDialog = false
                navController.popBackStack()
            }
        )
    }
}