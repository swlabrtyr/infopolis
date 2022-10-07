package com.infopolis.infopolis.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.infopolis.infopolis.data.model.CitiesListViewModel


@Composable
fun CitySearchListScreen(
    viewModel: CitiesListViewModel,
    navController: NavController
) {
    Scaffold(topBar = {
        val textSearch by viewModel.textSearch.collectAsState()
        TopAppBar {
            Text(text = "Infopolis", modifier = Modifier.padding(10.dp))
            Surface {
                SearchBar(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .fillMaxWidth(),
                    viewModel = viewModel,
                    input = textSearch
                )
            }
        }
    }) {
        Column(
            modifier = Modifier
                .background(Color.Black)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            val cities by viewModel.citiesList.collectAsState()
            if (cities == null) {
                CircularProgressIndicator()
            } else {
                CitiesList(
                    navController = navController,
                    cities = cities!!
                )
            }
        }
    }
}

