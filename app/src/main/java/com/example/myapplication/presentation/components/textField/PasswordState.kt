package com.example.myapplication.presentation.components.textField

const val PASSWORD_EMPTY_ERROR = "Password Cannot be empty"
const val PASSWORD_SMALL_ERROR = "Password must be at least 4 Characters"
const val PASSWORD_DO_NOT_MATCH_ERROR = "Passwords don't match"

class PasswordState(
    initialValue: String = ""
) : TextFieldState(
    initialValue = initialValue,
    validator = ::isPasswordValid,
    errorFor = ::passwordValidationError
)

private fun isPasswordValid(password: String): Boolean {
    return password.length > 3 && password.isNotBlank()
}

private fun passwordValidationError(password: String): String {
    return when {
        password.isBlank() -> PASSWORD_EMPTY_ERROR
        password.length <= 3 -> PASSWORD_SMALL_ERROR
        else -> ""
    }
}

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return when {
            showErrors() -> passwordConfirmationError(passwordState.text, text)
            else -> null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private fun passwordConfirmationError(password: String, confirmedPassword: String): String {
    return when {
        confirmedPassword.isBlank() -> PASSWORD_EMPTY_ERROR
        confirmedPassword.length <= 3 -> PASSWORD_SMALL_ERROR
        confirmedPassword != password -> PASSWORD_DO_NOT_MATCH_ERROR
        else -> ""
    }
}