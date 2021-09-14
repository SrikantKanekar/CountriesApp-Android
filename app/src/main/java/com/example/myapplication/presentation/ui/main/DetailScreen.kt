package com.example.myapplication.presentation.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

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
            Image(
                painter = rememberImagePainter(country.flag),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 20.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {

                    Text(
                        text = country.name,
                        fontWeight = FontWeight.W500,
                        style = MaterialTheme.typography.h4,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 2.dp),
                        text = country.capital,
                        style = MaterialTheme.typography.body1
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(text = "Languages", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                for (lang in country.languages){
                    Text(text = lang.name)
                }

                Spacer(modifier = Modifier.height(25.dp))

                Text(text = "Currencies", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                for (currency in country.currencies){
                    Text(text = currency.name)
                }
            }
        }
    }
}