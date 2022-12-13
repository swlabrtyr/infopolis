package com.infopolis.infopolis.data.model

interface ListViewModel {
    fun addCityToFavorites(city: CityInfo) : Boolean
    fun removeCityFromFavorites(city: CityInfo)
}