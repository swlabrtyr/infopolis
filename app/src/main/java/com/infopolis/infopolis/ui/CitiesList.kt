package com.infopolis.infopolis.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.infopolis.infopolis.CITY_DETAIL_SCREEN
import com.infopolis.infopolis.data.model.CityInfo
import com.infopolis.infopolis.util.encodeUrl

@Composable
fun CitiesList(
    navController: NavController,
    cities: List<CityInfo>?,
    onFavorite: (CityInfo) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = cities ?: emptyList(), key = { it.id }) { city ->
            CityListItem(
                name = city.name,
                imageUrl = city.imageUrl,
                isVisible = city.isVisible.value,
                isFavorite = city.isFavorite.value,
                onFavorite = { onFavorite(city) },
                onSelect = {
                    navController.navigate(
                        CITY_DETAIL_SCREEN + "/${city.name}" + "/${city.imageUrl?.encodeUrl()}"
                    )
                }
            )
        }
    }
}
