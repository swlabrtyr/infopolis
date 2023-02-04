package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
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
    isFavorite: Boolean,
    onFavorite: () -> Unit,
    onSelect: () -> Unit
) {
    val currentContext = LocalContext.current
    Surface(
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                .background(Color.Transparent)
                .height(90.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .clickable { onSelect() }
                    .fillMaxWidth(fraction = 0.8f)
            ) {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(currentContext)
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
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier.padding(15.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .fillMaxWidth()
                    .fillMaxHeight()

            ) {
                Icon(
                    if (isFavorite) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = "Add city to list of favorites",
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clickable {
                            onFavorite()
                        }
                        .align(Center)
                )
            }
        }
    }
}