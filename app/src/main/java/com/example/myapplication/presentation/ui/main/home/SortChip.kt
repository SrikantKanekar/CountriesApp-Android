package com.example.myapplication.presentation.ui.main.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.theme.primary
import com.example.myapplication.presentation.theme.primaryExtraLight
import com.example.myapplication.presentation.theme.surface

@Composable
fun SortChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {
    Surface(
        modifier = Modifier.padding(horizontal = 8.dp),
        elevation = 1.dp,
        shape = RoundedCornerShape(percent = 50),
        color = if (isSelected) primary else primaryExtraLight
    ) {
        TextButton(
            onClick = {
                onSelectedCategoryChanged(category)
                onExecuteSearch()
            }
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.overline,
                color = if (isSelected) surface else primary
            )
        }
    }
}