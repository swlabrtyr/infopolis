package com.infopolis.infopolis.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.infopolis.infopolis.CITY_DETAIL_SCREEN
import com.infopolis.infopolis.data.model.CityInfo
import com.infopolis.infopolis.util.encodeUrl

@Composable
fun CitiesList(
    navController: NavController,
    cities: List<CityInfo>?,
    onFavorite: (CityInfo) -> Unit,
    isFavoriteList: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = cities ?: emptyList(), key = { it.id }) { city ->
            // Only remove visibility for list items in favorites list
            val isVisible = if (isFavoriteList && !city.isFavorite.value) false else city.isVisible.value
            AnimatedVisibility(
                visible = isVisible,
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                CityListItem(
                    name = city.name,
                    imageUrl = city.imageUrl,
                    isFavorite = city.isFavorite.value,
                    onFavorite = {
                        onFavorite(city)
                    },
                    onSelect = {
                        navController.navigate(
                            CITY_DETAIL_SCREEN + "/${city.name}" + "/${city.imageUrl?.encodeUrl()}"
                        )
                    }
                )
            }
        }
    }
}
