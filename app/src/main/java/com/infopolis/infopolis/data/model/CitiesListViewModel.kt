package com.infopolis.infopolis.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infopolis.infopolis.repository.CityInfoRepository
import com.infopolis.infopolis.util.trimName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class CityInfo(val name: String? = null, val imageUrl: String? = null)

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    private val repository: CityInfoRepository,
) : ViewModel() {
    private val _textSearch = MutableStateFlow("")
    val textSearch: StateFlow<String> = _textSearch.asStateFlow()

    val citiesList = _textSearch
        .debounce(650)
        .map { searchInput ->
            val results = repository.getCities(searchInput).data?._embedded?.citySearchResults

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
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), initialValue = listOf())

    fun searchCities(searchInput: String) {
        _textSearch.value = searchInput
    }
}
