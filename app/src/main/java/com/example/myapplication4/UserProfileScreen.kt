package com.example.myapplication4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication4.components.CenteredTitleTopBar
import com.example.myapplication4.data.User
import com.example.myapplication4.ui.viewmodels.AuthViewModel

@Composable
fun UserProfileScreenWithLogout(navController: NavController, authViewModel: AuthViewModel) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val currentUserId by authViewModel.currentUserId.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

    UserProfileScreen(
        onBackClick = { navController.popBackStack() },
        onLogoutClick = {
            authViewModel.resetState()
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        },
        onEditClick = { navController.navigate("update_profile_screen") },
        currentUser = currentUser,
        currentUserId = currentUserId,
        isLoading = isLoading
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    currentUser: User?,
    currentUserId: String?,
    isLoading: Boolean
) {
    Scaffold(
        topBar = {
            CenteredTitleTopBar(
                title = "User Profile",
                onBackClick = onBackClick,
                showBackIcon = true
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(26.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFCEC0)
                    ),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text(
                        text = "Edit",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(26.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF6B6B)
                    ),
                    shape = RoundedCornerShape(26.dp)
                ) {
                    Text(
                        text = "Logout",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFF5F5)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.85f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFFACD)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.usericon),
                        contentDescription = "User Profile Image",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (isLoading) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFFFFCEC0),
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Loading user data...",
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                    } else if (currentUser != null) {
                        Text(
                            text = currentUser.name,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentUser.phone,
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentUser.email,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "User data not available",
                                fontSize = 20.sp,
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Current User ID: ${currentUserId ?: "None"}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )

                            Text(
                                text = "User Object: ${currentUser?.toString() ?: "null"}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}