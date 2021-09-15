package com.example.myapplication.presentation.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.components.MyButton

@Composable
fun LogoutDialog(
    visible: Boolean,
    logout: () -> Unit,
    dismiss: () -> Unit
){
    if (visible){
        AlertDialog(
            title = { Text(text = "Are You Sure?") },
            buttons = {
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.End
                    ),
                ) {
                    MyButton(
                        text = "Cancel",
                        onClick = dismiss,
                    )
                    MyButton(
                        text = "Yes",
                        onClick = logout,
                    )
                }
            },
            onDismissRequest = dismiss
        )
    }
}