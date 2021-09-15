package com.example.myapplication.presentation.ui.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import com.example.myapplication.presentation.navigation.Main.Detail
import com.example.myapplication.presentation.navigation.Main.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyTopAppBar(
    currentRoute: String?,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    popBackStack: () -> Unit,
    openSortMenu: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    Home.route -> "Home"
                    Detail.route -> "Detail"
                    else -> ""
                }
            )
        },
        navigationIcon = {
            when (currentRoute) {
                Home.route -> {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Menu, "Menu")
                    }
                }
                Detail.route -> {
                    IconButton(
                        onClick = popBackStack
                    ) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                }
            }
        },
        actions = {
            if (currentRoute == Home.route) {
                IconButton(
                    onClick = openSortMenu
                ) {
                    Icon(Icons.Filled.Sort, "Sort")
                }
            }
        }
    )
}