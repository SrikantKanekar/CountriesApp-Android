package com.example.myapplication.presentation.ui.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.myapplication.presentation.ui.main.MainViewModel

@ExperimentalCoilApi
@Composable
fun DetailScreen(
    name: String,
    viewModel: MainViewModel,
) {
    val country = viewModel.viewState.value.countries?.find { it.name == name }

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
                    .padding(vertical = 20.dp)
            ) {

                Text(
                    text = country.name,
                    fontWeight = FontWeight.W500,
                    style = MaterialTheme.typography.h4,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = country.capital,
                    style = MaterialTheme.typography.body1
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(text = "Languages", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                for (lang in country.languages) {
                    Text(text = lang.name)
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(text = "Currencies", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                for (currency in country.currencies) {
                    Text(text = currency.name ?: "")
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "Region : ${country.region}",
                    style = MaterialTheme.typography.body1
                )

                Text(
                    text = "population : ${country.population}",
                    style = MaterialTheme.typography.body1
                )

                Text(
                    text = "alpha3Code : ${country.alpha3Code}",
                    style = MaterialTheme.typography.body1
                )

                Text(
                    text = "area : ${country.area.toLong()} sq Km",
                    style = MaterialTheme.typography.body1
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(text = "Borders", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                for (border in country.borders) {
                    Text(text = border)
                }

            }
        }
    }
}