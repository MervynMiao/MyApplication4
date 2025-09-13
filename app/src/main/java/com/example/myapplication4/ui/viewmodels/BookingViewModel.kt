// BookingViewModel.kt
package com.example.myapplication4.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication4.data.Booking
import com.example.myapplication4.data.Pet
import com.example.myapplication4.data.Service
import com.example.myapplication4.data.User
import com.example.myapplication4.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingViewModel(private val repository: BookingRepository) : ViewModel() {
    private val _selectedService = MutableStateFlow<Service?>(null)
    val selectedService: StateFlow<Service?> = _selectedService

    private val _selectedPet = MutableStateFlow<Pet?>(null)
    val selectedPet: StateFlow<Pet?> = _selectedPet

    private val _selectedDate = MutableStateFlow<String?>(null)
    val selectedDate: StateFlow<String?> = _selectedDate

    private val _selectedTime = MutableStateFlow<String?>(null)
    val selectedTime: StateFlow<String?> = _selectedTime

    private val _userBookings = MutableStateFlow<List<Booking>>(emptyList())
    val userBookings: StateFlow<List<Booking>> = _userBookings

    fun setSelectedService(service: Service) {
        _selectedService.value = service
    }

    fun setSelectedPet(pet: Pet) {
        _selectedPet.value = pet
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun setSelectedTime(time: String) {
        _selectedTime.value = time
    }

    fun createBooking(
        userId: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = _selectedService.value
                val pet = _selectedPet.value
                val date = _selectedDate.value
                val time = _selectedTime.value

                if (service == null || pet == null || date == null || time == null) {
                    onError(Exception("Missing booking information"))
                    return@launch
                }

                val booking = Booking(
                    id = generateBookingId(),
                    serviceId = service.id,
                    serviceName = service.name,
                    petId = pet.id,
                    petName = pet.name,
                    userId = userId,
                    date = date,
                    timeSlot = time,
                    price = service.price
                )

                repository.addBooking(booking)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    fun getUserBookings(userId: String) {
        viewModelScope.launch {
            try {
                val bookings = repository.getUserBookings(userId)
                _userBookings.value = bookings
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun generateBookingId(): String {
        return "B${System.currentTimeMillis()}"
    }

    fun clearBookingData() {
        _selectedService.value = null
        _selectedPet.value = null
        _selectedDate.value = null
        _selectedTime.value = null
    }
}