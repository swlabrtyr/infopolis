package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
            .background(Color.White)
            .padding(10.dp),
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