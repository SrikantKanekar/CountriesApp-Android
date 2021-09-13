package com.example.myapplication.model

data class Country(
    val name: String,
    val capital: String,
    val flag: String,
    val languages: List<Language>,
    val currencies: List<Currency>
)
