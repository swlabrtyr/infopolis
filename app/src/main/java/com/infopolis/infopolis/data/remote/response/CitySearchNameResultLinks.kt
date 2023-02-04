package com.infopolis.infopolis.data.remote.response

import com.google.gson.annotations.SerializedName

data class CitySearchNameResultLinks(
    @SerializedName("city:item") val cityItem : CityItem
)