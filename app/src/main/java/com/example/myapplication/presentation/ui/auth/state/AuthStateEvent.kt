package com.example.myapplication.presentation.ui.auth.state

import com.example.myapplication.utils.StateEvent

sealed class AuthStateEvent : StateEvent {

    data class RegisterAttemptEvent(
        val name: String,
        val mobile: String,
        val email: String,
        val password: String,
    ) : AuthStateEvent() {
        override fun errorInfo() = "Registration attempt failed"
        override fun eventName() = "RegisterAttemptEvent"
        override fun shouldDisplayProgressBar() = true
    }

    data class LoginAttemptEvent(
        val email: String,
        val password: String
    ) : AuthStateEvent() {
        override fun errorInfo() = "Login attempt failed"
        override fun eventName() = "LoginAttemptEvent"
        override fun shouldDisplayProgressBar() = true
    }

    object CheckPreviousAuthEvent : AuthStateEvent() {
        override fun errorInfo() = "Error checking for previously authenticated user"
        override fun eventName() = "CheckPreviousAuthEvent"
        override fun shouldDisplayProgressBar() = false
    }
}