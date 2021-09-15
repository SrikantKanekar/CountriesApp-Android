package com.example.myapplication.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.SettingPreferences
import com.example.myapplication.SettingPreferences.*
import com.example.myapplication.SettingPreferences.Theme.*
import com.example.myapplication.presentation.navigation.Main
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    theme: Theme,
    toggleTheme: (Theme) -> Unit,
    deleteAccount: () -> Unit,
    logout: () -> Unit,
) {
    Column {

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_round),
            contentDescription = "",
            modifier = Modifier
                .height(150.dp)
                .padding(horizontal = 20.dp)
                .padding(vertical = 30.dp)
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
                .height(55.dp)
                .padding(start = 20.dp)
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
                .height(55.dp)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Theme",
                fontSize = 18.sp
            )
            Switch(
                checked = theme == DARK,
                onCheckedChange = {
                    toggleTheme(if (it) DARK else LIGHT)
                }
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { deleteAccount() })
                .height(55.dp)
                .padding(start = 20.dp)
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
                .height(55.dp)
                .padding(start = 20.dp)
        ) {
            Text(
                text = "Logout",
                fontSize = 18.sp
            )
        }
    }
}