package com.example.myapplication.presentation.ui.main.state

import com.example.myapplication.model.Country

data class MainViewState(
    var countries: List<Country>? = ArrayList(),
)