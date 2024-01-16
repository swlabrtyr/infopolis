package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.infopolis.infopolis.R
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.infopolis.infopolis.data.remote.response.CityScoreInfo
import com.infopolis.infopolis.data.remote.response.ScoreCategory
import com.infopolis.infopolis.util.Constants.IMAGE_URL
import com.infopolis.infopolis.util.formatText

@Composable
fun CityDetailScreen(
    cityScoreInfo: CityScoreInfo?,
    cityName: String?,
    image: @Composable RowScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row {
            image()
        }

        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            cityName?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
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
                    color = MaterialTheme.colorScheme.primary
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
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Left
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                text = category.score_out_of_10.toString(),
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Left
            )
        }
    }
}


@Preview
@Composable
fun CityDetailScreen_Preview() {
    CityDetailScreen(
        cityName = "Metropolis",
        cityScoreInfo = CityScoreInfo(summary = "Lorem Ipsum Doler"),
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(IMAGE_URL)
                .crossfade(true)
                .crossfade(1000)
                .error(R.drawable.ic_launcher_background)
                .build(),
            loading = {
                CircularProgressIndicator()
            },
            contentDescription = "Information about Metropolis",
            modifier = Modifier
                .size(500.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.FillBounds,
        )
    }
}