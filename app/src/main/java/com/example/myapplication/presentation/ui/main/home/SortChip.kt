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
import com.example.myapplication.SettingPreferences.Theme
import com.example.myapplication.presentation.theme.background
import com.example.myapplication.presentation.theme.primaryLight

@Composable
fun SortChip(
    appTheme: Theme,
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,
    onExecuteSearch: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        elevation = if (appTheme == Theme.DARK) {
            if (isSelected) 60.dp else 2.dp
        } else 1.dp,
        shape = RoundedCornerShape(percent = 100),
        color = if (appTheme == Theme.LIGHT) {
            if (isSelected) primaryLight else background
        } else MaterialTheme.colors.surface
    ) {
        TextButton(
            onClick = {
                onSelectedCategoryChanged(category)
                onExecuteSearch()
            }
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}