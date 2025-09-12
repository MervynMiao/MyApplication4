// AuthViewModel.kt
package com.example.myapplication4.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.myapplication4.data.User

class AuthViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    // AuthViewModel.kt - Add this function to the class
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _authState.value = AuthState.Loading

                // Check if user exists with this email and password
                val querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .whereEqualTo("password", password)
                    .get()
                    .await()

                if (querySnapshot.isEmpty) {
                    _authState.value = AuthState.Error("Invalid email or password")
                    return@launch
                }

                // Get user data
                val document = querySnapshot.documents[0]
                val user = User(
                    id = document.id,
                    name = document.getString("name") ?: "",
                    email = document.getString("email") ?: "",
                    phone = document.getString("phone") ?: "",
                    securityAnswer = document.getString("securityAnswer") ?: ""
                )

                _currentUser.value = user
                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Sign in failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun signUp(name: String, email: String, password: String, phone: String, securityAnswer: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _authState.value = AuthState.Loading

                // Check if email already exists
                val querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    _authState.value = AuthState.Error("Email already exists")
                    return@launch
                }

                // Create user data
                val user = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "password" to password, // Note: In a real app, you should hash passwords
                    "phone" to phone,
                    "securityAnswer" to securityAnswer
                )

                // Add user to Firestore
                db.collection("users")
                    .add(user)
                    .await()

                _authState.value = AuthState.Success
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Sign up failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }
}