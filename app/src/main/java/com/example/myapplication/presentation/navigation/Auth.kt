package com.example.myapplication.presentation.navigation

sealed class Auth(
    val route: String,
) {
    object LauncherScreen : Auth("LauncherScreen")
    object LoginScreen : Auth("LoginScreen")
    object RegisterScreen : Auth("RegisterScreen")
}