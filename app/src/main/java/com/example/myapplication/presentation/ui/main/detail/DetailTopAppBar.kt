package com.example.myapplication.presentation.ui.main.detail

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun DetailTopAppBar(
    popBackStack: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Detail") },
        navigationIcon = {
            IconButton(
                onClick = popBackStack
            ) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        }
    )
}