package com.example.myapplication.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.presentation.navigation.Main
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    deleteAccount: () -> Unit,
    logout: () -> Unit,
) {
    Column {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "",
            modifier = Modifier
                .height(100.dp)
                .padding(20.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    navController.navigate(Main.Home.route) {
                        popUpTo(Main.Home.route) { inclusive = true }
                    }
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
                .height(45.dp)
                .padding(start = 10.dp)
        ) {
            Text(
                text = "Home",
                fontSize = 18.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { deleteAccount() })
                .height(45.dp)
                .padding(start = 10.dp)
        ) {
            Text(
                text = "Delete Account",
                fontSize = 18.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { logout() })
                .height(45.dp)
                .padding(start = 10.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 18.sp
            )
        }
    }
}