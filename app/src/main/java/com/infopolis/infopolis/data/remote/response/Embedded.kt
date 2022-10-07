package com.infopolis.infopolis.data.remote.response

import com.google.gson.annotations.SerializedName

data class Embedded(
    @SerializedName("city:search-results") val citySearchResults: List<CitySearchNameResult>
)