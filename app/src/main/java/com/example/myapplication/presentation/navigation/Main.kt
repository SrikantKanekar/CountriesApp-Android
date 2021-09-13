package com.example.myapplication.presentation.navigation

sealed class Main(
    val route: String,
) {
    object Home : Main("Home")
    object Detail : Main("Detail")
}