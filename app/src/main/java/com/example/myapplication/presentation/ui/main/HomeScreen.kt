package com.example.myapplication.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myapplication.model.Country
import com.example.myapplication.presentation.components.MyButton

@ExperimentalCoilApi
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
                CountryCard(country = country) {
                    navController.navigate("Detail/${country.name}")
                }
            }
        }
    }

    if (viewModel.sortDialog.value) {
        AlertDialog(
            modifier = Modifier,
            title = { Text(text = "Sort Countries") },
            text = {
                Column {
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

@ExperimentalCoilApi
@Composable
fun CountryCard(
    country: Country,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = rememberImagePainter("https://restcountries.eu/data/afg.svg"),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 16.dp, top = 8.dp)
            ) {
                Text(
                    text = country.name,
                    fontWeight = FontWeight.W500,
                    style = MaterialTheme.typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.padding(start = 1.dp),
                    text = country.capital,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun RadioOption(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.width(160.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = name)
    }

}