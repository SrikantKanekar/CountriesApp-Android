package com.example.myapplication.presentation.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun LauncherScreenLogo() {
    AppLogo()
}

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp
) {
    val painterResource = painterResource(R.drawable.ic_launcher_round)
    Image(
        modifier = modifier.requiredSize(size),
        painter = painterResource,
        contentDescription = "Logo"
    )
}