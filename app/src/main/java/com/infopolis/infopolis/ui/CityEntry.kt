package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.infopolis.infopolis.util.Constants

@Composable
fun CityListItem(
    modifier: Modifier = Modifier,
    name: String?,
    imageUrl: String?,
    onSelect: () -> Unit
) {
    val context = LocalContext.current
    Surface(shape = RoundedCornerShape(12.dp)) {
        Row(
            modifier = Modifier
                .background(Color.DarkGray)
                .clickable { onSelect() }
                .height(100.dp),
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUrl ?: Constants.IMAGE_URL)
                    .crossfade(true)
                    .crossfade(1000)
                    .build(),
                loading = {
                    CircularProgressIndicator()
                },
                contentDescription = "Picture of $name",
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .paddingFromBaseline(5.dp),
                contentScale = ContentScale.FillBounds,
            )

            if (name != null) {
                Column(
                    modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.body1,
                        color = Color.White,
                        modifier = Modifier.padding(15.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}