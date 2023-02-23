package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.infopolis.infopolis.data.model.CityInfo

@Composable
fun CityTabList(
    modifier: Modifier,
    cities: List<CityInfo>?,
    navController: NavController,
    onFavorite: (CityInfo) -> Unit,
    isFavoriteList: Boolean
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        if (cities == null) {
            CircularProgressIndicator()
        } else {
            CitiesList(
                navController = navController,
                cities = cities,
                isFavoriteList = isFavoriteList,
                onFavorite = onFavorite,
            )
        }
    }
}