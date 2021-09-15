package com.example.myapplication.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.model.Setting
import com.example.myapplication.presentation.components.Drawer
import com.example.myapplication.presentation.components.MyButton
import com.example.myapplication.presentation.navigation.Main
import com.example.myapplication.presentation.theme.ApplicationTheme
import com.example.myapplication.presentation.ui.BaseActivity
import com.example.myapplication.presentation.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
            val scope = rememberCoroutineScope()
            val mainViewModel: MainViewModel = viewModel()
            val settings = mainViewModel.settingFlow.collectAsState(initial = Setting())

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            ApplicationTheme(
                theme = appTheme,
                displayProgressBar = mainViewModel.shouldDisplayProgressBar.value,
                scaffoldState = scaffoldState,
                stateMessage = mainViewModel.stateMessage.value,
                removeStateMessage = { mainViewModel.removeStateMessage() }
            ) {

                Scaffold(
                    scaffoldState = scaffoldState,
                    snackbarHost = { scaffoldState.snackbarHostState },
                    topBar = {
                        TopAppBar(
                            title = { Text(text = if (currentRoute == "Home") "Countries" else "Country") },
                            navigationIcon = {
                                if (currentRoute == "Home") {
                                    IconButton(onClick = {
                                        scope.launch {
                                            scaffoldState.drawerState.open()
                                        }
                                    }) {
                                        Icon(Icons.Filled.Menu, "")
                                    }
                                } else {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Icon(Icons.Filled.ArrowBack, "")
                                    }
                                }
                            },
                            actions = {
                                if (currentRoute == "Home") {
                                    IconButton(onClick = {
                                        mainViewModel.sortDialog.value = true
                                    }) {
                                        Icon(Icons.Filled.Sort, "")
                                    }
                                }
                            }
                        )
                    },
                    drawerContent = {
                        Drawer(
                            scope = scope,
                            scaffoldState = scaffoldState,
                            navController = navController,
                            theme = settings.value.theme,
                            toggleTheme = {
                                mainViewModel.setTheme(it)
                            },
                            deleteAccount = { mainViewModel.deleteUser() },
                            logout = {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                                mainViewModel.logoutDialog.value = true
                            }
                        )
                    }
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = Main.Home.route
                    ) {

                        composable(route = Main.Home.route) {
                            HomeScreen(
                                viewModel = mainViewModel,
                                navController = navController
                            )
                        }

                        composable(route = "Detail/{country}") { backStackEntry ->
                            DetailScreen(
                                name = backStackEntry.arguments?.getString("country") ?: "",
                                viewModel = mainViewModel
                            )
                        }
                    }

                    if (mainViewModel.logoutDialog.value) {
                        AlertDialog(
                            modifier = Modifier,
                            title = { Text(text = "Are You Sure?") },
                            buttons = {
                                Spacer(modifier = Modifier.height(30.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        10.dp,
                                        Alignment.End
                                    ),
                                ) {
                                    MyButton(
                                        text = "Yes",
                                        onClick = {
                                            mainViewModel.logoutDialog.value = false
                                            mainViewModel.logout()
                                        },
                                    )
                                    MyButton(
                                        text = "Cancel",
                                        onClick = {
                                            mainViewModel.logoutDialog.value = false
                                        },
                                    )
                                }
                            },
                            onDismissRequest = {
                                mainViewModel.logoutDialog.value = false
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.cachedToken.observe(this, { authToken ->
            if (authToken == null || authToken.account_pk == -1 || authToken.token == null) {
                navAuthActivity()
            }
        })
    }

    private fun navAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}