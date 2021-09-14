package com.example.myapplication.presentation.components.textField

const val USERNAME_LARGE_ERROR = "Username cannot exceed 20 characters"
const val USERNAME_EMPTY_ERROR = "Username cannot be empty"

class UsernameState(
    initialValue: String = ""
) : TextFieldState(
    initialValue = initialValue,
    validator = ::isUsernameValid,
    errorFor = ::usernameValidationError
)

private fun isUsernameValid(username: String): Boolean {
    return username.length <= 20 && username.isNotBlank()
}

private fun usernameValidationError(username: String): String {
    return when {
        username.length > 20 -> USERNAME_LARGE_ERROR
        else -> USERNAME_EMPTY_ERROR
    }
}