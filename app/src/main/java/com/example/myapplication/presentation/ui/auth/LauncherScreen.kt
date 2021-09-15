package com.example.myapplication.presentation.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.presentation.components.MyTextButton
import com.example.myapplication.presentation.components.image.LauncherScreenLogo
import com.example.myapplication.presentation.navigation.Auth.LoginScreen
import com.example.myapplication.presentation.navigation.Auth.RegisterScreen

@Composable
fun LauncherScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(170.dp))

        LauncherScreenLogo()

        MyTextButton(
            modifier = Modifier
                .padding(top = 50.dp)
                .padding(bottom = 15.dp),
            text = "Login",
            onClick = { navController.navigate(LoginScreen.route) }
        )

        MyTextButton(
            text = "Register",
            onClick = { navController.navigate(RegisterScreen.route) }
        )
    }
}