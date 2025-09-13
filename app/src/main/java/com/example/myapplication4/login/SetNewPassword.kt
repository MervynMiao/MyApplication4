package com.example.myapplication4.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import com.example.myapplication4.R
import com.example.myapplication4.components.CenteredTitleTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication4.ui.viewmodels.AuthViewModel
import java.util.regex.Pattern

@Composable
fun SetNewPasswordScreen(
    navController: NavController,
    email: String? = null,
    authViewModel: AuthViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var userId by remember { mutableStateOf<String?>(null) }

    // Track validation states
    var isPasswordValid by remember { mutableStateOf(true) }
    var isConfirmPasswordValid by remember { mutableStateOf(true) }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    // Password validation function (same as SignUpScreen)
    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        val hasLetter = Pattern.compile("[a-zA-Z]").matcher(password).find()
        val hasDigit = Pattern.compile("[0-9]").matcher(password).find()
        return hasLetter && hasDigit
    }

    // Validate password and update states
    fun validatePassword() {
        isPasswordValid = newPassword.isEmpty() || isValidPassword(newPassword)
        passwordError = if (newPassword.isNotEmpty() && !isValidPassword(newPassword)) {
            "Must be at least 8 characters with letters and numbers"
        } else {
            ""
        }
    }

    // Validate confirm password and update states
    fun validateConfirmPassword() {
        isConfirmPasswordValid = confirmPassword.isEmpty() || newPassword == confirmPassword
        confirmPasswordError = if (confirmPassword.isNotEmpty() && newPassword != confirmPassword) {
            "Passwords do not match"
        } else {
            ""
        }
    }

    // Check if button should be enabled - FIXED
    val isButtonEnabled = remember(newPassword, confirmPassword, isPasswordValid, isConfirmPasswordValid, isLoading) {
        newPassword.isNotEmpty() &&
                confirmPassword.isNotEmpty() &&
                isPasswordValid &&
                isConfirmPasswordValid &&
                !isLoading
    }

    LaunchedEffect(email) {
        if (!email.isNullOrEmpty()) {
            // Get username from email
            authViewModel.getUsernameByEmail(email) { retrievedUsername ->
                if (!retrievedUsername.isNullOrEmpty()) {
                    username = retrievedUsername
                }
            }

            // Get user ID from email - DON'T verify security answer again
            authViewModel.getUserIdByEmail(email) { retrievedUserId ->
                if (!retrievedUserId.isNullOrEmpty()) {
                    userId = retrievedUserId
                } else {
                    // Fallback: if we can't get user ID by email, try another approach
                    errorMessage = "Unable to find user. Please try the password reset process again."
                    showErrorDialog = true
                }
            }
        }
    }

    // Validate on password changes
    LaunchedEffect(newPassword) {
        validatePassword()
        validateConfirmPassword()
    }

    // Validate on confirm password changes
    LaunchedEffect(confirmPassword) {
        validateConfirmPassword()
    }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                "Change Password",
                onBackClick = { navController.popBackStack() },
                showBackIcon = false // Add this parameter
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFF1ED))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(2.dp, Color.Black),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFBFFE8)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(750.dp)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.forgetpassword),
                        contentDescription = "Password Reset",
                        modifier = Modifier.size(120.dp)
                    )

                    Text(
                        text = "Set New Password",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        enabled = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                    )

                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        isError = !isPasswordValid && newPassword.isNotEmpty(),
                        supportingText = {
                            if (passwordError.isNotEmpty()) {
                                Text(passwordError)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm New Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        isError = !isConfirmPasswordValid && confirmPassword.isNotEmpty(),
                        supportingText = {
                            if (confirmPasswordError.isNotEmpty()) {
                                Text(confirmPasswordError)
                            }
                        }
                    )

                    Button(
                        onClick = {
                            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                                errorMessage = "Please fill in all fields"
                                showErrorDialog = true
                                return@Button
                            }

                            if (!isPasswordValid) {
                                errorMessage = "Password must be at least 8 characters with both letters and numbers"
                                showErrorDialog = true
                                return@Button
                            }

                            if (!isConfirmPasswordValid) {
                                errorMessage = "Passwords do not match"
                                showErrorDialog = true
                                return@Button
                            }

                            // Update password in Firebase
                            if (userId != null) {
                                isLoading = true
                                authViewModel.updatePassword(userId!!, newPassword) { success, message ->
                                    isLoading = false
                                    if (success) {
                                        showDialog = true
                                    } else {
                                        errorMessage = message ?: "Password update failed"
                                        showErrorDialog = true
                                    }
                                }
                            } else {
                                errorMessage = "User not found. Please try the password reset process again."
                                showErrorDialog = true
                            }
                        },
                        enabled = isButtonEnabled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isButtonEnabled) Color(0xFFECEC81) else Color.Gray
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Change Password",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFDD4C8)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(50.dp)
                            .align(Alignment.Start)
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

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text(errorMessage) },
            confirmButton = {
                Button(
                    onClick = { showErrorDialog = false }
                ) {
                    Text("OK")
                }
            }
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                            navController.popBackStack("login", false)
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFDD4C8),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("OK", fontSize = 20.sp)
                    }
                }
            },
            containerColor = Color(0xFFFFF1ED),
            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.popupicon),
                        contentDescription = "Success",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(160.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Password Changed",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                }
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Your password has been changed successfully",
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}