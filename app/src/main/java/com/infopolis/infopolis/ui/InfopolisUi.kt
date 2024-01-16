package com.infopolis.infopolis.ui

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.infopolis.infopolis.data.model.CityInfo


@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfopolisUi(
    citySearch: (String) -> Unit,
    defaultCities: List<CityInfo>?,
    toggleFavorite: (CityInfo) -> Unit,
    navController: NavController,
    textSearch: String,
    cities: List<CityInfo>?,
    favCities: List<CityInfo>?,
    toggleDefaults: () -> Unit,
    showDefaults: Boolean = false,
) {
    val bottomNavTabs = listOf("Cities", "Favorites")
    var selectedBottomTab by rememberSaveable { mutableIntStateOf(0) }

    val scrollBehavior = enterAlwaysScrollBehavior(rememberTopAppBarState())
    val isCollapsed = remember { derivedStateOf { scrollBehavior.state.collapsedFraction < 1.0 } }

    Scaffold(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.onPrimary,
        topBar = {
            if (selectedBottomTab == 0) {
                CenterAlignedTopAppBar(
                    title = {
                        AnimatedVisibility(
                            visible = isCollapsed.value,
                            enter = slideInVertically() + fadeIn(),
                            exit = slideOutVertically() + fadeOut()
                        ) {
                            SearchBar(
                                input = textSearch,
                                onChange = citySearch,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    scrollBehavior = scrollBehavior,
                )
            }
        },
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                NavigationBar {
                    bottomNavTabs.forEachIndexed { index, item ->
                        NavigationBarItem(
                            modifier = Modifier
                                .background(
                                    color = if (selectedBottomTab == index) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .fillMaxSize()
                                .padding(top = 0.dp),
                            icon = {
                                if (index == 0) {
                                    Icon(
                                        Icons.Filled.Home,
                                        contentDescription = "Home tab",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                } else if (index == 1) {
                                    Icon(
                                        Icons.Filled.Favorite,
                                        contentDescription = "Favorites tab",
                                        tint = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = item, color = if (selectedBottomTab == index) {
                                        MaterialTheme.colorScheme.background
                                    } else {
                                        MaterialTheme.colorScheme.secondary
                                    }
                                )
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
            } else if (cities.isEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Button(
                            onClick = { toggleDefaults() },
                        ) {
                            Text(
                                text = "Take me somewhere",
                                color = Color.White,
                                fontStyle = FontStyle.Italic,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                if (showDefaults) {
                    if (defaultCities.isNullOrEmpty()){
                        CircularProgressIndicator()
                    } else {
                        CityTabList(
                            modifier = Modifier.padding(
                                top = contentPadding.calculateTopPadding(),
                                start = 10.dp,
                                end = 10.dp,
                                bottom = 80.dp
                            ),
                            cities = defaultCities,
                            navController = navController,
                            isFavoriteList = false,
                            onFavorite = toggleFavorite
                        )
                    }
                }
            } else {
                CityTabList(
                    modifier = Modifier.padding(
                        top = contentPadding.calculateTopPadding(),
                        start = 10.dp,
                        end = 10.dp,
                        bottom = 80.dp
                    ),
                    cities = cities,
                    navController = navController,
                    isFavoriteList = false,
                    onFavorite = toggleFavorite
                )
            }
        } else {
            CityTabList(
                modifier = Modifier.padding(
                    top = contentPadding.calculateTopPadding(),
                    start = 10.dp,
                    end = 10.dp,
                    bottom = 80.dp
                ),
                cities = favCities,
                navController = navController,
                isFavoriteList = true,
                onFavorite = toggleFavorite
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