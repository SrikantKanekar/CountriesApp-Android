package com.example.myapplication.presentation.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.presentation.components.MyTextButton
import com.example.myapplication.presentation.components.image.LauncherScreenLogo
import com.example.myapplication.presentation.navigation.Auth


@Composable
fun DetailScreen(
    country: String,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(150.dp))

        Text(text = "Detail Screen of $country")
    }
}