package com.example.myapplication.model

import com.example.myapplication.SettingPreferences.Theme
import com.example.myapplication.SettingPreferences.Theme.LIGHT

data class Setting(
    val theme: Theme = LIGHT
)