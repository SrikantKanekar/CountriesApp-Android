package com.example.myapplication.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.presentation.components.image.AppLogo
import com.example.myapplication.presentation.navigation.Auth
import com.example.myapplication.presentation.theme.ApplicationTheme
import com.example.myapplication.presentation.ui.BaseActivity
import com.example.myapplication.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private val viewModel: AuthViewModel by viewModels()

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
                            startDestination = Auth.LauncherScreen.route
                        ) {

                            composable(route = Auth.LauncherScreen.route) {
                                LauncherScreen(navController = navController)
                            }

                            composable(route = Auth.LoginScreen.route) {
                                LoginScreen(viewModel = authViewModel)
                            }

                            composable(route = Auth.RegisterScreen.route) {
                                RegisterScreen(viewModel = authViewModel)
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AppLogo(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }

    private fun subscribeObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect { viewState ->
                viewState.token?.let {
                    sessionManager.login(it)
                }
            }
        }
        sessionManager.cachedToken.observe(this, { token ->
            token.let { authToken ->
                if (authToken != null && authToken.account_pk != -1 && authToken.token != null) {
                    navMainActivity()
                }
            }
        })
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}