package com.example.myapplication.presentation.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.presentation.components.MyTextButton
import com.example.myapplication.presentation.components.image.LauncherScreenLogo
import com.example.myapplication.presentation.navigation.Auth


@Composable
fun LauncherScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(150.dp))

        LauncherScreenLogo()

        MyTextButton(
            modifier = Modifier
                .padding(top = 50.dp)
                .padding(bottom = 10.dp),
            text = "Login",
            onClick = { navController.navigate(Auth.LoginScreen.route) }
        )

        MyTextButton(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Register",
            onClick = { navController.navigate(Auth.RegisterScreen.route) }
        )
    }
}