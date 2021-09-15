package com.example.myapplication.model

import com.example.myapplication.presentation.ui.main.home.Region

data class Country(
    val name: String,
    val capital: String,
    val flag: String,
    val region: Region,
    val population: Long,
    val area: Double,
    val alpha3Code: String,
    val borders: List<String>,
    val languages: List<Language>,
    val currencies: List<Currency>
)
