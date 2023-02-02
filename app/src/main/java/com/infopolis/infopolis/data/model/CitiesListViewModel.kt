package com.infopolis.infopolis.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infopolis.infopolis.repository.CityInfoRepository
import com.infopolis.infopolis.util.trimName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

data class CityInfo(
    val name: String? = null,
    val imageUrl: String? = null,
    var isFavorite: MutableState<Boolean> = mutableStateOf(false),
    val isVisible: MutableState<Boolean> = mutableStateOf(true),
    val id: UUID = UUID.randomUUID(),
)

@HiltViewModel
class CitiesListViewModel @Inject constructor(private val repository: CityInfoRepository) :
    ViewModel() {

    private val _textSearch = MutableStateFlow("")
    val textSearch: StateFlow<String> = _textSearch.asStateFlow()

    fun searchCities(searchInput: String) {
         _textSearch.value = searchInput
    }

    @FlowPreview
    private fun fetchCities(textSearch: StateFlow<String>): StateFlow<List<CityInfo>?> {
        return textSearch
            .debounce(500)
            .map { searchInput ->
                // Do not return the default list of cities if no user input
                if (searchInput == "") return@map listOf()
                val results = repository.getCities(searchInput).data
                    ?._embedded
                    ?.citySearchResults

                val nameList = results?.map { result ->
                    withContext(viewModelScope.coroutineContext) {
                        result.matching_full_name.trimName()
                    }
                }.orEmpty()

                val imageRequests = nameList.map { name ->
                    viewModelScope.async {
                        repository.getCityImage(name).data
                            ?.photos
                            ?.get(0)
                            ?.image
                            ?.mobile
                    }
                }.awaitAll()

                results?.zip(imageRequests)?.map { (name, url) ->
                    CityInfo(name.matching_full_name, url)
                }
            }
            .map { cities ->
                cities?.filter { city -> city.imageUrl != null }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = listOf())
    }

    @FlowPreview
    val citiesList = fetchCities(textSearch = _textSearch)

    private val _favoriteCities = MutableStateFlow<List<CityInfo>?>(emptyList())
    val favoriteCities = _favoriteCities.asStateFlow()
    private val favList = mutableListOf<CityInfo>()

    @FlowPreview
    private fun addCityToFavorites(city: CityInfo) {
        city.isFavorite.value = true
        favList.add(city)
        _favoriteCities.value = favList
        Timber.i("::Favorites ${(_favoriteCities.value as MutableList<CityInfo>).forEach { it.name }}")
    }

    private fun removeCityFromFavorites(city: CityInfo) {
        city.isFavorite.value = false
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
