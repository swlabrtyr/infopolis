package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.infopolis.infopolis.data.model.CitiesListViewModel
import com.infopolis.infopolis.data.model.CityInfo
import kotlinx.coroutines.FlowPreview


@OptIn(FlowPreview::class, ExperimentalMaterial3Api::class)
@Composable
fun InfopolisUi(
    viewModel: CitiesListViewModel,
    navController: NavController,
    textSearch: String,
    cities: List<CityInfo>?,
    favCities: List<CityInfo>?
) {
    var selectedBottomTab by remember { mutableStateOf(0) }
    val bottomNavTabs = listOf("Cities", "Favorites")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Infopolis") },
                actions = {
                    SearchBar(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        onChange = viewModel::searchCities,
                        input = textSearch
                    )
                }
            )
        },
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                NavigationBar(
                    modifier = Modifier.padding(bottom = 20.dp)
                ) {
                    bottomNavTabs.forEachIndexed { index, item ->
                        NavigationBarItem(
                            modifier = Modifier
                                .background(
                                    color = if (selectedBottomTab == index) {
                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .fillMaxWidth(),
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
        }) { contentPadding ->
        if (selectedBottomTab == 0) {
            if (cities == null) {
                CircularProgressIndicator()
            } else {
                CityTabList(
                    modifier = Modifier.padding(contentPadding),
                    cities = cities,
                    navController = navController,
                    onFavorite = viewModel::toggleCityFavorite
                )
            }
        } else {
            CityTabList(
                modifier = Modifier.padding(contentPadding),
                cities = favCities,
                navController = navController,
                onFavorite = { city ->
                    // Animate city removal from favorites list
                    city.isVisible.value = false
                    viewModel.toggleCityFavorite(city)
                })
        }
    }
}

@Composable
fun CityTabList(
    modifier: Modifier,
    cities: List<CityInfo>?,
    navController: NavController,
    onFavorite: (CityInfo) -> Unit
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
                onFavorite = onFavorite
            )
        }
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}