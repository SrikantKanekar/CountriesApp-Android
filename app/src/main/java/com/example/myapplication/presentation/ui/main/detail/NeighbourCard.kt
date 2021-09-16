package com.example.myapplication.presentation.ui.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.myapplication.SettingPreferences.Theme
import com.example.myapplication.model.Country

@ExperimentalCoilApi
@Composable
fun NeighbourCard(
    country: Country,
    appTheme: Theme,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = MaterialTheme.shapes.large,
        elevation = if (appTheme == Theme.DARK) 4.dp else 0.dp
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
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
                    .width(100.dp)
                    .padding(top = 5.dp)
            ) {
                Text(
                    text = country.name,
                    fontWeight = FontWeight.W400,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = country.capital,
                    fontSize = 11.sp,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}