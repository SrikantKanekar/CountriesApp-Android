package com.example.myapplication.presentation.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.navigation.Main
import com.example.myapplication.presentation.theme.ApplicationTheme
import com.example.myapplication.presentation.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme(darkTheme = isDark.value) {

                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                val mainViewModel: MainViewModel = viewModel()
                val viewState = mainViewModel.viewState.collectAsState()

                ApplicationTheme(
                    darkTheme = isDark.value,
                    scaffoldState = scaffoldState,
                    stateMessage = mainViewModel.stateMessage.value,
                    removeStateMessage = { mainViewModel.removeStateMessage() }
                ) {

                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = Main.Home.route
                        ) {

                            composable(route = Main.Home.route) {
                                HomeScreen(navController = navController)
                            }

                            composable(route = Main.Detail.route) {
                                DetailScreen(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}