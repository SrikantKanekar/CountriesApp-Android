package com.example.myapplication.presentation.ui.main.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.myapplication.presentation.ui.main.MainViewModel

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavController
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(viewModel.countries) { country ->
            CountryCard(
                country = country,
                onClick = { navController.navigate("Detail/${country.name}") }
            )
        }
    }


    if (viewModel.sortDialog.value) {
        AlertDialog(
            modifier = Modifier.width(300.dp),
            title = { Text(text = "Sort Countries") },
            text = {
                Column {
                    Spacer(modifier = Modifier.height(50.dp))
                    RadioOption(
                        name = "Ascending",
                        selected = viewModel.ascending.value,
                        onClick = { viewModel.setAscending() }
                    )
                    RadioOption(
                        name = "Descending",
                        selected = !viewModel.ascending.value,
                        onClick = { viewModel.setDescending() }
                    )
                }
            },
            buttons = {},
            onDismissRequest = {
                viewModel.sortDialog.value = false
            }
        )
    }
}

@Composable
fun RadioOption(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .width(160.dp)
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = name)
    }
}