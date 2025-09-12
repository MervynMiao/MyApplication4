// NavGraph.kt (updated)
package com.example.myapplication4

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication4.login.LoadingScreen
import com.example.myapplication4.login.LoginScreen
import com.example.myapplication4.login.SignUpScreen
import com.example.myapplication4.ui.viewmodels.AuthViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()

    // Observe auth state changes using collectAsState
    val authState = authViewModel.authState
    val isLoading = authViewModel.isLoading

    // Handle navigation based on auth state
    LaunchedEffect(authState.collectAsState().value) {
        when (authState.value) {
            is com.example.myapplication4.ui.viewmodels.AuthState.Success -> {
                // Navigate to home screen after successful sign up
                navController.navigate("home") {
                    popUpTo("signup") { inclusive = true }
                }
                // Reset state after navigation
                authViewModel.resetState()
            }
            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = "loadingScreen"
    ) {
        composable("loadingScreen") {
            LoadingScreen(navController = navController)
        }
        composable("login") {
            LoginScreen(
                navController = navController,
                onSignIn = { email, password -> /* Handle sign in */ },
                authState = authState.collectAsState().value,
                isLoading = isLoading.collectAsState().value
            )
        }
        composable("signup") {
            SignUpScreen(
                navController = navController,
                onSignUp = { name, email, password, phone, securityAnswer ->
                    authViewModel.signUp(name, email, password, phone, securityAnswer)
                },
                authState = authState.collectAsState().value,
                isLoading = isLoading.collectAsState().value
            )
        }
        composable("home") {
            // Simple home screen for now
            androidx.compose.material3.Text("Welcome to Home Screen!")
        }
    }
}