package com.infopolis.infopolis.data.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infopolis.infopolis.data.remote.response.CityScoreInfo
import com.infopolis.infopolis.repository.CityInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityDetailViewModel @Inject constructor(
    private val repository: CityInfoRepository,
) : ViewModel() {
    private val _cityScoreInfo = MutableStateFlow(value = CityScoreInfo())
    val cityScoreInfo: StateFlow<CityScoreInfo?> = _cityScoreInfo.asStateFlow()

    fun getCityScore(cityName: String?) {
        viewModelScope.launch {
            _cityScoreInfo.value = repository.getCityScoreInfo(cityName).data ?: CityScoreInfo()
        }
    }
}