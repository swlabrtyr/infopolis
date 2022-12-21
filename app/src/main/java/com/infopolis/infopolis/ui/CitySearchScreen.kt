package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.infopolis.infopolis.data.model.CitiesListViewModel


@Composable
fun InfopolisUi(
    citiesListViewModel: CitiesListViewModel,
    navController: NavController
) {
    val cities by citiesListViewModel.citiesList.collectAsState()
    val favorites by citiesListViewModel.favCitiesList.collectAsState()

    var selectedBottomTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            val textSearch by citiesListViewModel.textSearch.collectAsState()
            TopAppBar {
                Text(text = "Infopolis", modifier = Modifier.padding(10.dp))
                Surface {
                    SearchBar(
                        modifier = Modifier
                            .background(MaterialTheme.colors.primary)
                            .fillMaxWidth(),
                        viewModel = citiesListViewModel,
                        input = textSearch
                    )
                }
            }
        },
        bottomBar = {
            val items = listOf("Cities", "Favorites")
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                BottomNavigation {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            modifier = Modifier.background(
                                color = if (selectedBottomTab == index) {
                                    MaterialTheme.colors.primaryVariant.copy(alpha = 0.7f)
                                } else {
                                    Color.Transparent
                                }
                            ),
                            icon = {
                                if (index == 0) {
                                    Icon(Icons.Filled.Home, contentDescription = null)
                                } else if (index == 1) {
                                    Icon(Icons.Filled.Favorite, contentDescription = null)
                                }
                            },
                            label = {
                                Text(item)
                            },
                            selected = selectedBottomTab == index,
                            onClick = {
                                selectedBottomTab = index
                            }
                        )
                    }
                }
            }
        }) {
        if (selectedBottomTab == 0) {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                if (cities == null) {
                    CircularProgressIndicator()
                } else {
                    CitiesList(
                        navController = navController,
                        cities = cities!!
                    ) { city ->
                        if (city.isFavorite.value) {
                            citiesListViewModel.removeCityFromFavorites(city)
                        } else {
                            citiesListViewModel.addCityToFavorites(city)
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                CitiesList(
                    navController = navController,
                    cities = favorites,
                ) { city ->
                    if (city.isFavorite.value) {
                        citiesListViewModel.removeCityFromFavorites(city)
                    } else {
                        citiesListViewModel.addCityToFavorites(city)
                    }
                }
            }
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}