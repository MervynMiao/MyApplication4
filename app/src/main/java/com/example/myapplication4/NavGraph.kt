package com.example.myapplication4

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication4.login.ForgotScreen
import com.example.myapplication4.login.LoadingScreen
import com.example.myapplication4.login.LoginScreen
import com.example.myapplication4.login.SetNewPasswordScreen
import com.example.myapplication4.login.SignUpScreen
import com.example.myapplication4.ui.viewmodels.AuthViewModel
import com.example.myapplication4.ui.viewmodels.PetViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    // Create the AuthViewModel at the NavGraph level to ensure it's shared
    val authViewModel: AuthViewModel = viewModel()

    // Observe auth state changes using collectAsState
    val authState by authViewModel.authState.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()
    val currentUser by authViewModel.currentUser.collectAsState()
    val currentUserId by authViewModel.currentUserId.collectAsState()

    // Handle navigation based on auth state
    LaunchedEffect(authState) {
        when (authState) {
            is com.example.myapplication4.ui.viewmodels.AuthState.Success -> {
                if (currentUser != null) {
                    navController.navigate("main_menu") {
                        popUpTo(0) { inclusive = true }
                    }
                }
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
                onSignIn = { email, password ->
                    authViewModel.signIn(email, password)
                },
                authState = authState,
                isLoading = isLoading
            )
        }
        composable("signup") {
            SignUpScreen(
                navController = navController,
                onSignUp = { name, email, password, phone, securityAnswer ->
                    authViewModel.signUp(name, email, password, phone, securityAnswer)
                },
                authState = authState,
                isLoading = isLoading
            )
        }
        composable("forgot") {
            ForgotScreen(navController = navController)
        }
        composable(
            "set_new_password/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            SetNewPasswordScreen(navController = navController, email = email)
        }
        composable("main_menu") {
            MainMenuScreen(navController = navController)
        }
        composable("pet_screen") {
            MyPetScreen(navController = navController)
        }
        composable("add_pet_screen") {
            val petViewModel: PetViewModel = viewModel()
            AddPetScreen(navController = navController, petViewModel = petViewModel)
        }
        composable("edit_pet_screen/{petId}") { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId") ?: ""
            val petViewModel: PetViewModel = viewModel()
            EditPetScreen(navController = navController, petViewModel = petViewModel, petId = petId)
        }
        composable("user_profile_screen") {
            // Pass the shared authViewModel instead of creating a new one
            UserProfileScreenWithLogout(navController = navController, authViewModel = authViewModel)
        }
        composable("update_profile_screen") {
            // Pass the shared authViewModel instead of creating a new one
            UpdateProfileScreen(navController = navController, authViewModel = authViewModel)
        }
        composable("addBooking") {
            AddBookingScreen(navController)
        }
        composable(
            "bookingFlow1/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.StringType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getString("serviceId") ?: ""
            BookingFlow1Screen(navController, viewModel(), serviceId) // Change hiltViewModel() to viewModel()
        }

        composable("bookingFlow2") {
            BookingFlow2Screen(navController, viewModel()) // Change hiltViewModel() to viewModel()
        }

        composable("bookingFlow3") {
            PetSelectionScreen(navController, viewModel(), viewModel()) // Change hiltViewModel() to viewModel()
        }
    }
}