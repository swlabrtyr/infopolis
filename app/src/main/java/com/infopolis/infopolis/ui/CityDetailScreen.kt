package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.base.R
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.infopolis.infopolis.data.model.CityDetailViewModel
import com.infopolis.infopolis.data.remote.response.ScoreCategory
import com.infopolis.infopolis.util.formatText
import com.infopolis.infopolis.util.trimName

@Composable
fun CityDetailScreen(
    viewModel: CityDetailViewModel,
    cityName: String?,
    cityImageUrl: String? = null,
) {
    val cityScoreInfo by viewModel.cityScoreInfo.collectAsState()
    val scrollState = rememberScrollState()

    viewModel.getCityScore(cityName?.trimName())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(bottom = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cityImageUrl)
                    .crossfade(true)
                    .crossfade(1000)
                    .error(R.drawable.ic_100tb)
                    .build(),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = "Information about $cityName",
                modifier = Modifier
                    .size(500.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.FillBounds,
            )
        }

        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            cityName?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left,
                )
            }
        }

        Row {
            if (cityScoreInfo?.summary == "") {
                CircularProgressIndicator()
            } else {
                Text(
                    modifier = Modifier.padding(15.dp),
                    text = cityScoreInfo?.summary.toString().formatText(),
                    color = Color.White
                )
            }
        }

        cityScoreInfo?.categories?.let {
            ScoreList(categoriesList = it)
        }
    }
}

@Composable
fun ScoreList(
    categoriesList: List<ScoreCategory>
) {
    Column {
        for (category in categoriesList) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                text = category.name,
                fontSize = 30.sp,
                color = Color.White,
                textAlign = TextAlign.Left
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                text = category.score_out_of_10.toString(),
                color = Color.White,
                textAlign = TextAlign.Left
            )
        }
    }
}
