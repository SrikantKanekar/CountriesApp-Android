package com.example.myapplication.presentation.navigation

sealed class Main(
    val route: String,
) {
    object List : Main("List")
    object Detail : Main("Detail")
}