package com.example.myapplication.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.navigation.Auth.*
import com.example.myapplication.presentation.theme.ApplicationTheme
import com.example.myapplication.presentation.ui.BaseActivity
import com.example.myapplication.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()

        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            val authViewModel: AuthViewModel = viewModel()
            val viewState = authViewModel.viewState.collectAsState()

            ApplicationTheme(
                theme = appTheme,
                displayProgressBar = authViewModel.shouldDisplayProgressBar.value,
                scaffoldState = scaffoldState,
                stateMessage = authViewModel.stateMessage.value,
                removeStateMessage = { authViewModel.removeStateMessage() }
            ) {

                if (viewState.value.previousUserCheck == true) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }
                    ) {

                        NavHost(
                            navController = navController,
                            startDestination = LauncherScreen.route
                        ) {

                            composable(route = LauncherScreen.route) {
                                LauncherScreen(navController = navController)
                            }

                            composable(route = LoginScreen.route) {
                                LoginScreen(viewModel = authViewModel)
                            }

                            composable(route = RegisterScreen.route) {
                                RegisterScreen(viewModel = authViewModel)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun subscribeObservers() {
        lifecycleScope.launchWhenStarted {
            emailDataStore.preferenceFlow.collect { email ->
                if (email.isNotBlank()) navMainActivity()
            }
        }
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}