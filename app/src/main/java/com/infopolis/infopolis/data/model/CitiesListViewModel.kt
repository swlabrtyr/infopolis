package com.infopolis.infopolis.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

data class CityInfo(
    val name: String? = null,
    val imageUrl: String? = null,
    var isFavorite: MutableState<Boolean> = mutableStateOf(false),
    var isVisible: MutableState<Boolean> = mutableStateOf(true),
    var id: UUID = UUID.randomUUID(),
    var likedFromCitiesList: Boolean = true
)

@HiltViewModel
class CitiesListViewModel @Inject constructor() : ViewModel() {

    private val _favoriteCities = MutableStateFlow<List<CityInfo>?>(emptyList())
    private val favCities = mutableListOf<CityInfo>()
    val favoriteCities = _favoriteCities.asStateFlow()

    @FlowPreview
    private fun addCityToFavorites(city: CityInfo) {
        city.isFavorite.value = true
        favCities.add(city)
        _favoriteCities.value = favCities.distinctBy { it.name }
    }

    private fun removeCityFromFavorites(city: CityInfo) {
        city.isFavorite.value = false
        favCities.remove(city)
    }

    @OptIn(FlowPreview::class)
    fun toggleCityFavorite(city: CityInfo) {
        if (city.isFavorite.value) {
            removeCityFromFavorites(city)
        } else {
            addCityToFavorites(city)
        }
    }
}
