package com.infopolis.infopolis

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.infopolis.infopolis.ui.CityDetailScreen
import com.infopolis.infopolis.ui.InfopolisUi

const val CITY_LIST_SCREEN = "cities_list"
const val CITY_DETAIL_SCREEN = "city_detail"
const val CITY_NAME_ARG = "city_name"
const val CITY_IMAGE_URL_ARG = "city_image_url"

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = CITY_LIST_SCREEN
    ) {
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
        ) {
            CityDetailScreen(
                cityName = it.arguments?.getString(CITY_NAME_ARG),
                cityImageUrl = it.arguments?.getString(CITY_IMAGE_URL_ARG),
                viewModel = hiltViewModel()
            )
        }

        composable(route = CITY_LIST_SCREEN) {
            InfopolisUi(
                citiesListViewModel = hiltViewModel(),
                navController = navController,
            )
        }
    }
}