package com.example.myapplication.presentation.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.myapplication.presentation.theme.ApplicationTheme
import com.example.myapplication.presentation.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme(darkTheme = isDark.value) {


            }
        }
    }
}