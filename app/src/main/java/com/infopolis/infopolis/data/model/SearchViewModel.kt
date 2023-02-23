package com.infopolis.infopolis.data.model

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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: CityInfoRepository) :
    ViewModel() {

    private val _textSearch = MutableStateFlow("")
    val textSearch: StateFlow<String> = _textSearch.asStateFlow()

    fun searchCities(searchInput: String) {
        _textSearch.value = searchInput
    }

    // space will return the default list of cities
    @OptIn(FlowPreview::class)
    val defaultCities = fetchCities(textSearch = MutableStateFlow(" "))

    @FlowPreview
    fun fetchCities(textSearch: StateFlow<String>): StateFlow<List<CityInfo>?> {
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
}