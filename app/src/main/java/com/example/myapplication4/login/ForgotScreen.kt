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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication4.R
import com.example.myapplication4.components.CenteredTitleTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication4.ui.viewmodels.AuthViewModel
import java.util.regex.Pattern

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var securityAnswer by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var userId by remember { mutableStateOf<String?>(null) }

    // Email validation states
    var isEmailValid by remember { mutableStateOf(true) }
    var emailError by remember { mutableStateOf("") }
    var isEmailVerified by remember { mutableStateOf(false) }

    // Email validation function
    fun isValidEmail(email: String): Boolean {
        val emailPattern = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        )
        return emailPattern.matcher(email).matches()
    }

    // Validate email and update states
    fun validateEmail() {
        isEmailValid = email.isEmpty() || isValidEmail(email)
        emailError = if (email.isNotEmpty() && !isValidEmail(email)) {
            "Please enter a valid email address"
        } else {
            ""
        }
    }

    // Check if button should be enabled
    val isButtonEnabled = remember(email, securityAnswer, isLoading, isEmailValid) {
        email.isNotEmpty() &&
                securityAnswer.isNotEmpty() &&
                isEmailValid &&
                !isLoading
    }

    // Validate on email changes
    LaunchedEffect(email) {
        validateEmail()
        isEmailVerified = false // Reset verification when email changes
    }

    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                "Forget Username or Password",
                onBackClick = { navController.popBackStack() }
                // showBackIcon defaults to true, so no need to specify
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
                        contentDescription = "Pets",
                        modifier = Modifier.size(120.dp)
                    )

                    Text(
                        text = "Forget Username & Password",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        isError = !isEmailValid && email.isNotEmpty(),
                        supportingText = {
                            if (emailError.isNotEmpty()) {
                                Text(emailError)
                            }
                        }
                    )

                    OutlinedTextField(
                        value = securityAnswer,
                        onValueChange = { securityAnswer = it },
                        label = { Text("What is your favourite animal?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    )

                    Button(
                        onClick = {
                            if (email.isEmpty() || securityAnswer.isEmpty()) {
                                errorMessage = "Please fill in all fields"
                                showErrorDialog = true
                                return@Button
                            }

                            if (!isEmailValid) {
                                errorMessage = "Please enter a valid email address"
                                showErrorDialog = true
                                return@Button
                            }

                            isLoading = true
                            authViewModel.verifySecurityAnswer(email, securityAnswer) { success, message ->
                                isLoading = false
                                if (success) {
                                    userId = message // message contains user ID
                                    isEmailVerified = true
                                    showDialog = true
                                } else {
                                    errorMessage = message ?: "Verification failed. Please check your email and security answer."
                                    showErrorDialog = true
                                    isEmailVerified = false
                                }
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
                                "Verify",
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
                            // Navigate to set new password screen with email as parameter
                            navController.navigate("set_new_password/$email")
                        },
                        modifier = Modifier
                            .width(180.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFDD4C8),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Next", fontSize = 20.sp)
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
                        text = "Verify Successful",
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
                        text = "You can set your new password now",
                        textAlign = TextAlign.Center
                    )
                }
            }
        )
    }
}