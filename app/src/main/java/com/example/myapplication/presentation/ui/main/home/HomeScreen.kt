package com.example.myapplication.presentation.ui.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.myapplication.SettingPreferences.Theme
import com.example.myapplication.presentation.ui.main.MainViewModel

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavController,
    appTheme: Theme
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(viewModel.countries) { country ->
            CountryCard(
                country = country,
                onClick = { navController.navigate("Detail/${country.name}") },
                appTheme = appTheme
            )
        }
    }
}