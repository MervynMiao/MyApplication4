package com.example.myapplication4.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.example.myapplication4.data.User
import java.util.regex.Pattern

class AuthViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _currentUserId = MutableStateFlow<String?>(null)
    val currentUserId: StateFlow<String?> = _currentUserId

    // In your signIn function:
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _authState.value = AuthState.Loading

                val querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .whereEqualTo("password", password)
                    .get()
                    .await()

                if (querySnapshot.isEmpty) {
                    _authState.value = AuthState.Error("Invalid email or password")
                    return@launch
                }

                val document = querySnapshot.documents[0]
                val user = User(
                    id = document.id,
                    name = document.getString("name") ?: "",
                    email = document.getString("email") ?: "",
                    phone = document.getString("phone") ?: "",
                    securityAnswer = document.getString("securityAnswer") ?: ""
                )

                // Set both user and userId immediately
                _currentUserId.value = document.id
                _currentUser.value = user
                _authState.value = AuthState.Success

                // Set user in session
                UserSession.setUser(document.id, user.email)

            } catch (e: Exception) {
                _authState.value = AuthState.Error("Sign in failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // In your signUp function:
    fun signUp(name: String, email: String, password: String, phone: String, securityAnswer: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _authState.value = AuthState.Loading

                // Check if email already exists
                val emailQuery = db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                if (!emailQuery.isEmpty) {
                    _authState.value = AuthState.Error("Email already exists")
                    return@launch
                }

                // Check if phone number already exists
                val phoneQuery = db.collection("users")
                    .whereEqualTo("phone", phone)
                    .get()
                    .await()

                if (!phoneQuery.isEmpty) {
                    _authState.value = AuthState.Error("Phone number already exists")
                    return@launch
                }

                // Create user data
                val user = hashMapOf(
                    "name" to name,
                    "email" to email,
                    "password" to password,
                    "phone" to phone,
                    "securityAnswer" to securityAnswer
                )

                // Add user to Firestore
                val documentRef = db.collection("users")
                    .add(user)
                    .await()

                // Create User object and set both userId and user data
                val newUser = User(
                    id = documentRef.id,
                    name = name,
                    email = email,
                    phone = phone,
                    securityAnswer = securityAnswer
                )

                _currentUserId.value = documentRef.id
                _currentUser.value = newUser
                _authState.value = AuthState.Success

                // Set user in session
                UserSession.setUser(documentRef.id, email)

            } catch (e: Exception) {
                _authState.value = AuthState.Error("Sign up failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkPhoneAvailability(phone: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("users")
                    .whereEqualTo("phone", phone)
                    .get()
                    .await()

                onResult(querySnapshot.isEmpty)
            } catch (e: Exception) {
                onResult(true)
            }
        }
    }

    fun checkEmailAvailability(email: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                onResult(querySnapshot.isEmpty)
            } catch (e: Exception) {
                onResult(true)
            }
        }
    }

    fun verifySecurityAnswer(email: String, securityAnswer: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                if (querySnapshot.isEmpty) {
                    onResult(false, "Email not found")
                    return@launch
                }

                val document = querySnapshot.documents[0]
                val storedSecurityAnswer = document.getString("securityAnswer") ?: ""

                if (storedSecurityAnswer.equals(securityAnswer, ignoreCase = true)) {
                    onResult(true, document.id)
                } else {
                    onResult(false, "Incorrect security answer")
                }
            } catch (e: Exception) {
                onResult(false, "Verification failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePassword(userId: String, newPassword: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                if (newPassword.length < 6) {
                    onResult(false, "Password must be at least 6 characters long")
                    return@launch
                }

                if (!newPassword.any { it.isDigit() }) {
                    onResult(false, "Password must contain at least one number")
                    return@launch
                }

                if (!newPassword.any { it.isLetter() }) {
                    onResult(false, "Password must contain at least one letter")
                    return@launch
                }

                db.collection("users")
                    .document(userId)
                    .update("password", newPassword)
                    .await()

                onResult(true, "Password updated successfully")
            } catch (e: Exception) {
                onResult(false, "Password update failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUsernameByEmail(email: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val username = document.getString("name") ?: ""
                    onResult(username)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun getUserIdByEmail(email: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val querySnapshot = db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    onResult(document.id)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                onResult(null)
            }
        }
    }

    fun updateUserProfile(userId: String, name: String, phone: String, securityAnswer: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                db.collection("users")
                    .document(userId)
                    .update(
                        mapOf(
                            "name" to name,
                            "phone" to phone,
                            "securityAnswer" to securityAnswer
                        )
                    )
                    .await()

                _currentUser.value = _currentUser.value?.copy(
                    name = name,
                    phone = phone,
                    securityAnswer = securityAnswer
                )

                onResult(true, "Profile updated successfully")
            } catch (e: Exception) {
                onResult(false, "Update failed: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // In your resetState function:
    fun resetState() {
        _authState.value = AuthState.Idle
        _currentUser.value = null
        _currentUserId.value = null
        _isLoading.value = false
        // Clear user session
        UserSession.clearUser()
    }
}