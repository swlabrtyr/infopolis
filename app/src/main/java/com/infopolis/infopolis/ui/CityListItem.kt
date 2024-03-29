package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.infopolis.infopolis.util.shimmerBackground

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
        modifier = Modifier.padding(vertical = 3.dp, horizontal = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .border(width = 1.dp, color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background)
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
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmerBackground()
                        )
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
                            color = MaterialTheme.colorScheme.onSecondary,
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
                    tint = MaterialTheme.colorScheme.primary,
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