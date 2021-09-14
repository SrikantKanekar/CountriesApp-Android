package com.example.myapplication.presentation.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val viewState = viewModel.viewState.collectAsState()

    viewState.value.countries?.let { countries ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 25.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(countries) { country ->
                Text(
                    modifier = Modifier.clickable {
                        navController.navigate("Detail/${country.name}")
                    },
                    text = country.name
                )
            }
        }
    }
}