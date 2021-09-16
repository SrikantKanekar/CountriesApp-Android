package com.example.myapplication.presentation.ui.main.detail

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

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
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 8.dp,
    )
}