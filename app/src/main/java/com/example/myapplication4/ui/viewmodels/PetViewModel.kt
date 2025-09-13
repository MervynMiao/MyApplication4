package com.example.myapplication4.ui.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication4.data.Pet
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PetViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    private val _pets = MutableStateFlow<List<Pet>>(emptyList())
    val pets: StateFlow<List<Pet>> = _pets

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Remove the setCurrentUser and clearCurrentUser methods since we're using UserSession

    fun getPetById(
        petId: String,
        onResult: (Boolean, Pet?, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                val document = db.collection("pets")
                    .document(petId)
                    .get()
                    .await()

                if (document.exists()) {
                    val pet = Pet(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        breed = document.getString("breed") ?: "",
                        gender = document.getString("gender") ?: "",
                        bod = document.getString("bod") ?: "",
                        category = document.getString("category") ?: "",
                        imageUrl = document.getString("imageUrl") ?: "",
                        userId = document.getString("userId") ?: ""
                    )
                    onResult(true, pet, null)
                } else {
                    onResult(false, null, "Pet not found")
                }
            } catch (e: Exception) {
                onResult(false, null, "Failed to get pet: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Add a new pet with image upload
    fun addPet(
        name: String,
        breed: String,
        gender: String,
        bod: String,
        category: String,
        imageUri: Uri? = null,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Check if user is logged in using UserSession
                val currentUserId = UserSession.currentUserId
                if (currentUserId == null) {
                    onResult(false, "User not logged in")
                    return@launch
                }

                var imageUrl = ""

                // Upload image if provided
                if (imageUri != null) {
                    imageUrl = uploadImage(imageUri)
                }

                // Create pet data with userId
                val pet = hashMapOf(
                    "name" to name,
                    "breed" to breed,
                    "gender" to gender,
                    "bod" to bod,
                    "category" to category,
                    "imageUrl" to imageUrl,
                    "userId" to currentUserId // Add userId field
                )

                // Add pet to Firestore
                val documentRef = db.collection("pets")
                    .add(pet)
                    .await()

                onResult(true, "Pet added successfully")
            } catch (e: Exception) {
                onResult(false, "Failed to add pet: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Get all pets for the current user only
    fun getAllPets() {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Check if user is logged in using UserSession
                val currentUserId = UserSession.currentUserId
                if (currentUserId == null) {
                    _pets.value = emptyList()
                    return@launch
                }

                val querySnapshot = db.collection("pets")
                    .whereEqualTo("userId", currentUserId) // Filter by current user
                    .get()
                    .await()

                val petsList = querySnapshot.documents.map { document ->
                    Pet(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        breed = document.getString("breed") ?: "",
                        gender = document.getString("gender") ?: "",
                        bod = document.getString("bod") ?: "",
                        category = document.getString("category") ?: "",
                        imageUrl = document.getString("imageUrl") ?: "",
                        userId = document.getString("userId") ?: ""
                    )
                }

                _pets.value = petsList
            } catch (e: Exception) {
                // Handle error
                _pets.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Update the other methods (getPetsByCategory, updatePet, deletePet) similarly:
    // Replace `currentUserId` with `UserSession.currentUserId` in all methods

    // For example, in updatePet:
    fun updatePet(
        petId: String,
        name: String,
        breed: String,
        gender: String,
        bod: String,
        category: String,
        imageUri: Uri? = null,
        onResult: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true

                // Check if user is logged in using UserSession
                val currentUserId = UserSession.currentUserId
                if (currentUserId == null) {
                    onResult(false, "User not logged in")
                    return@launch
                }

                // First check if the pet belongs to the current user
                val petDoc = db.collection("pets").document(petId).get().await()
                val petUserId = petDoc.getString("userId") ?: ""

                if (petUserId != currentUserId) {
                    onResult(false, "You don't have permission to edit this pet")
                    return@launch
                }

                var imageUrl = ""

                // Upload new image if provided
                if (imageUri != null) {
                    imageUrl = uploadImage(imageUri)
                } else {
                    // Keep existing image URL
                    imageUrl = petDoc.getString("imageUrl") ?: ""
                }

                // Update pet data
                val updatedData = hashMapOf<String, Any>(
                    "name" to name,
                    "breed" to breed,
                    "gender" to gender,
                    "bod" to bod,
                    "category" to category,
                    "imageUrl" to imageUrl
                )

                db.collection("pets")
                    .document(petId)
                    .update(updatedData)
                    .await()

                onResult(true, "Pet updated successfully")
            } catch (e: Exception) {
                onResult(false, "Failed to update pet: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Similarly update deletePet method

    // Upload image to Firebase Storage (unchanged)
    private suspend fun uploadImage(imageUri: Uri): String {
        val fileName = "pet_images/${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child(fileName)

        val uploadTask = imageRef.putFile(imageUri).await()
        return imageRef.downloadUrl.await().toString()
    }
}