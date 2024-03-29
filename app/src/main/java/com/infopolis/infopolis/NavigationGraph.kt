package com.infopolis.infopolis

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infopolis.infopolis.data.model.CitiesListViewModel
import com.infopolis.infopolis.data.model.CityDetailViewModel
import com.infopolis.infopolis.data.model.SearchViewModel
import com.infopolis.infopolis.ui.CityDetailScreen
import com.infopolis.infopolis.ui.InfopolisUi
import com.infopolis.infopolis.util.trimName
import kotlinx.coroutines.FlowPreview

const val CITY_LIST_SCREEN = "cities_list"
const val CITY_DETAIL_SCREEN = "city_detail"
const val CITY_NAME_ARG = "city_name"
const val CITY_IMAGE_URL_ARG = "city_image_url"

enum class AppState {
    Empty,
    DefaultList,
    SearchList,
    FavoritesList
}


@OptIn(FlowPreview::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    val searchViewModel = hiltViewModel<SearchViewModel>()

    val cityDetailViewModel = hiltViewModel<CityDetailViewModel>()
    val citiesListViewModel = hiltViewModel<CitiesListViewModel>()

    val textSearch by searchViewModel.textSearch.collectAsState()
    val cities by searchViewModel.citiesList.collectAsState()

    val favCities by citiesListViewModel.favoriteCities.collectAsState()
    val defaultCities by searchViewModel.defaultCities.collectAsState()

    var showDefaults by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = CITY_LIST_SCREEN
    ) {

        composable(
            route = CITY_LIST_SCREEN
        ) {
            InfopolisUi(
                navController = navController,
                textSearch = textSearch,
                cities = cities,
                defaultCities = defaultCities,
                showDefaults = showDefaults,
                favCities = favCities,
                toggleDefaults = { showDefaults = !showDefaults },
                toggleFavorite = citiesListViewModel::toggleCityFavorite,
                citySearch = searchViewModel::searchCities,
            )
        }

        composable(
            route = "$CITY_DETAIL_SCREEN/{$CITY_NAME_ARG}/{$CITY_IMAGE_URL_ARG}",
            arguments = listOf(
                navArgument(CITY_NAME_ARG) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(CITY_IMAGE_URL_ARG) {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString(CITY_NAME_ARG)
            val cityImageUrl = backStackEntry.arguments?.getString(CITY_IMAGE_URL_ARG)

            cityDetailViewModel.getCityScore(cityName?.trimName())
            CityDetailScreen(
                cityName = cityName,
                cityImageUrl = cityImageUrl,
                viewModel = cityDetailViewModel
            )
        }
    }
}