package com.example.myapplication.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.example.myapplication.model.Setting
import com.example.myapplication.presentation.navigation.Main.Home
import com.example.myapplication.presentation.theme.ApplicationTheme
import com.example.myapplication.presentation.ui.BaseActivity
import com.example.myapplication.presentation.ui.auth.AuthActivity
import com.example.myapplication.presentation.ui.main.detail.DetailScreen
import com.example.myapplication.presentation.ui.main.detail.DetailTopAppBar
import com.example.myapplication.presentation.ui.main.home.HomeScreen
import com.example.myapplication.presentation.ui.main.home.Region
import com.example.myapplication.presentation.ui.main.home.SearchAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoilApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()

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
                        when(currentRoute){
                            Home.route -> {
                                SearchAppBar(
                                    scope = scope,
                                    scaffoldState = scaffoldState,
                                    query = mainViewModel.query,
                                    onQueryChanged = { mainViewModel.onQueryChanged(it) },
                                    selectedCategory = mainViewModel.selectedCategory,
                                    onSelectedCategoryChanged = {
                                        mainViewModel.selectedCategoryChange(Region.valueOf(it))
                                    }
                                )
                            }
                            else -> {
                                DetailTopAppBar(
                                    popBackStack = { navController.popBackStack() }
                                )
                            }
                        }
                    },
                    drawerContent = {
                        Drawer(
                            scope = scope,
                            scaffoldState = scaffoldState,
                            navController = navController,
                            currentRoute = currentRoute,
                            theme = settings.value.theme,
                            toggleTheme = { mainViewModel.setTheme(it) },
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
                        startDestination = Home.route
                    ) {
                        composable(route = Home.route) {
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

                    LogoutDialog(
                        visible = mainViewModel.logoutDialog.value,
                        logout = {
                            mainViewModel.logoutDialog.value = false
                            mainViewModel.logout()
                        },
                        dismiss = {
                            mainViewModel.logoutDialog.value = false
                        }
                    )
                }
            }
        }
    }

    private fun subscribeObservers() {
        lifecycleScope.launchWhenStarted {
            emailDataStore.preferenceFlow.collect { email ->
                if (email.isBlank()) navAuthActivity()
            }
        }
    }

    private fun navAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}