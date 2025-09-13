package com.example.myapplication4.ui.viewmodels

object UserSession {
    var currentUserId: String? = null
    var currentUserEmail: String? = null

    fun setUser(userId: String, email: String? = null) {
        currentUserId = userId
        currentUserEmail = email
    }

    fun clearUser() {
        currentUserId = null
        currentUserEmail = null
    }
}