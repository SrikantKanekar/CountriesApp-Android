package com.example.myapplication.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.presentation.components.MyCircularProgressIndicator
import com.example.myapplication.presentation.components.snackbar.SnackbarController
import com.example.myapplication.presentation.components.stateMessageHandler.HandleMessageUiType
import com.example.myapplication.utils.StateMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = blue500,
    primaryVariant = blue800,
    secondary = blue500,
    secondaryVariant = blue800,
    background = Color.White,
    surface = Color.White,
    error = red500,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

private val DarkColorPalette = darkColors(
    primary = blue300,
    primaryVariant = blue800,
    secondary = blue300,
    background = darkBackground,
    surface = darkSurface,
    error = red300,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

val snackbarController = SnackbarController(CoroutineScope(Main))

@Composable
fun ApplicationTheme(
    darkTheme: Boolean,
    displayProgressBar: Boolean = false,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    stateMessage: StateMessage? = null,
    removeStateMessage: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            content()

            MyCircularProgressIndicator(
                isDisplayed = displayProgressBar,
                modifier = Modifier.align(Alignment.Center)
            )

            HandleMessageUiType(
                stateMessage = stateMessage,
                scaffoldState = scaffoldState,
                removeStateMessage = removeStateMessage
            )
        }
    }
}