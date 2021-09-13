package com.example.myapplication.network

import com.example.myapplication.model.Country
import retrofit2.http.GET

interface CountriesApi {

    @GET("/rest/v2/all")
    suspend fun getAllCountries(): List<Country>
}