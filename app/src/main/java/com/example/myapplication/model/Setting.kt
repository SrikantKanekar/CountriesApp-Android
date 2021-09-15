package com.example.myapplication.model

import com.example.myapplication.SettingPreferences.*
import com.example.myapplication.SettingPreferences.Theme.*

data class Setting(
    val theme: Theme = LIGHT
)