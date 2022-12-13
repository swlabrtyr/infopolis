package com.infopolis.infopolis.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infopolis.infopolis.repository.CityInfoRepository
import com.infopolis.infopolis.util.trimName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

data class CityInfo(
    val name: String? = null,
    val imageUrl: String? = null,
    var isFavorite: MutableState<Boolean> = mutableStateOf(false),
    val id: UUID = UUID.randomUUID()
)

@HiltViewModel
class CitiesListViewModel @Inject constructor(private val repository: CityInfoRepository) :
    ViewModel(), ListViewModel {

    private val _textSearch = MutableStateFlow("")
    val textSearch: StateFlow<String> = _textSearch.asStateFlow()

    private val _favCitiesList = MutableStateFlow(mutableListOf<CityInfo>())
    val favCitiesList: StateFlow<List<CityInfo>> = _favCitiesList.asStateFlow()

    override fun addCityToFavorites(city: CityInfo) = _favCitiesList.value.add(city)
    override fun removeCityFromFavorites(city: CityInfo) {
        val updatedList = _favCitiesList.value.filter { it.id != city.id }
        _favCitiesList.value = updatedList as MutableList<CityInfo>
    }


    @kotlinx.coroutines.FlowPreview
    val citiesList = _textSearch
        .debounce(500)
        .map { searchInput ->
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

    fun searchCities(searchInput: String) {
        _textSearch.value = searchInput
    }
}
