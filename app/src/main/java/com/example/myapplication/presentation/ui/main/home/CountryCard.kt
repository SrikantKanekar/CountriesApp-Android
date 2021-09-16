package com.example.myapplication.presentation.ui.main.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.example.myapplication.SettingPreferences
import com.example.myapplication.SettingPreferences.*
import com.example.myapplication.SettingPreferences.Theme.*
import com.example.myapplication.model.Country

@ExperimentalCoilApi
@Composable
fun CountryCard(
    country: Country,
    appTheme: Theme,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        elevation = if (appTheme == DARK) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {

            val imageLoader = ImageLoader
                .Builder(LocalContext.current)
                .componentRegistry {
                    add(SvgDecoder(LocalContext.current))
                }
                .build()

            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(shape = MaterialTheme.shapes.large),
                painter = rememberImagePainter(
                    data = country.flag,
                    imageLoader = imageLoader
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null
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