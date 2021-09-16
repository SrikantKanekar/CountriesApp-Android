package com.example.myapplication.presentation.ui.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.myapplication.SettingPreferences.Theme
import com.example.myapplication.presentation.components.DataText
import com.example.myapplication.presentation.components.TitleText
import com.example.myapplication.presentation.ui.main.MainViewModel

@ExperimentalCoilApi
@Composable
fun DetailScreen(
    name: String,
    viewModel: MainViewModel,
    appTheme: Theme,
    navController: NavController
) {
    val viewState = viewModel.viewState.collectAsState()
    val country = viewState.value.countries?.find { it.name == name }

    country?.let {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            val imageLoader = ImageLoader
                .Builder(LocalContext.current)
                .componentRegistry {
                    add(SvgDecoder(LocalContext.current))
                }
                .build()

            Image(
                painter = rememberImagePainter(
                    data = country.flag,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 25.dp)
            ) {

                Text(
                    text = country.name,
                    fontWeight = FontWeight.W500,
                    style = MaterialTheme.typography.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                DataText(text = country.capital)

                Spacer(modifier = Modifier.height(25.dp))

                TitleText(text = "Native Languages")
                for (lang in country.languages) {
                    DataText(text = lang.name)
                }

                Spacer(modifier = Modifier.height(25.dp))

                TitleText(text = if (country.currencies.size > 1) "Currencies" else "Currency")
                for (currency in country.currencies) {
                    if (currency.name != null) DataText(text = "${currency.symbol} ${currency.name}")
                }

                Spacer(modifier = Modifier.height(25.dp))

                TitleText(text = "Geographical data")
                DataText(text = "Region : ${country.region}")
                DataText(text = "Population : ${country.population}")
                DataText(text = "Area : ${country.area.toLong()} Km²")
            }

            if (country.borders.isNotEmpty()) {
                TitleText(
                    modifier = Modifier.padding(start = 20.dp, bottom = 12.dp),
                    text = "Neighbouring Countries",
                )
                LazyRow(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 35.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val neighbours = viewState.value.countries
                        ?.filter { country.borders.contains(it.alpha3Code) }
                        .orEmpty()
                    items(neighbours) { neighbour ->
                        NeighbourCard(
                            country = neighbour,
                            appTheme = appTheme,
                            onClick = { navController.navigate("Detail/${neighbour.name}") },
                        )
                    }
                }
            }
        }
    }
}