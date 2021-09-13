package com.example.myapplication.presentation.navigation

sealed class Navigation(
    val route: String,
){
    object Mode: Navigation("Mode")

    object Cooling: Navigation("Cooling")
    object CoolingDetail: Navigation("CoolingDetail")

    object Heating: Navigation("Heating")
    object HeatingDetail: Navigation("HeatingDetail")
}