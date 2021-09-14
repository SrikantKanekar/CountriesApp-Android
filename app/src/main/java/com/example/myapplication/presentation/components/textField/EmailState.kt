package com.example.myapplication.presentation.components.textField

import java.util.regex.Pattern

private const val EMAIL_VALIDATION_REGEX = "^(.+)@(.+)\$"
const val EMAIL_EMPTY_ERROR = "Email cannot be empty"
const val EMAIL_INVALID_ERROR = "Invalid email:"

class EmailState(
    initialValue: String = ""
) : TextFieldState(
    initialValue = initialValue,
    validator = ::isEmailValid,
    errorFor = ::emailValidationError
)

private fun isEmailValid(email: String): Boolean {
    return Pattern.matches(EMAIL_VALIDATION_REGEX, email) && email.isNotBlank()
}

private fun emailValidationError(email: String): String {
    return when {
        email.isBlank() -> EMAIL_EMPTY_ERROR
        else -> "$EMAIL_INVALID_ERROR $email"
    }
}
