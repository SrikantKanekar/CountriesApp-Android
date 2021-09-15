package com.example.myapplication.presentation.ui.main

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
import com.example.myapplication.SettingPreferences.*
import com.example.myapplication.SettingPreferences.Theme.*
import com.example.myapplication.presentation.navigation.Main
import com.example.myapplication.presentation.navigation.Main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navController: NavController,
    currentRoute: String?,
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
                .padding(22.dp)
                .padding(top = 30.dp)
        )

        DrawerItem(
            name = "Home",
            onClick = {
                if (currentRoute != Home.route){
                    navController.navigate(Home.route) {
                        popUpTo(Home.route) { inclusive = true }
                    }
                }
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        )

        DrawerItem(
            name = when (theme) {
                DARK -> "Dark"
                LIGHT -> "Light"
                else -> ""
            },
            onClick = { toggleTheme(if (theme == LIGHT) DARK else LIGHT) },
            switch = {
                Switch(
                    checked = theme == DARK,
                    onCheckedChange = {
                        toggleTheme(if (it) DARK else LIGHT)
                    }
                )
            }
        )

        DrawerItem(
            name = "Delete Account",
            onClick = { deleteAccount() }
        )

        DrawerItem(
            name = "Logout",
            onClick = { logout() }
        )
    }
}

@Composable
fun DrawerItem(
    name: String,
    onClick: () -> Unit,
    switch: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .height(55.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            fontSize = 18.sp
        )
        switch()
    }
}