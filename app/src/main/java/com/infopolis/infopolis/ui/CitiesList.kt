package com.infopolis.infopolis.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
    cities: List<CityInfo>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(cities) { city ->
            CityListItem(
                name = city.name,
                imageUrl = city.imageUrl
            ) {
                navController.navigate(
                    CITY_DETAIL_SCREEN + "/${city.name}" + "/${city.imageUrl?.encodeUrl()}"
                )
            }
        }
    }
}

