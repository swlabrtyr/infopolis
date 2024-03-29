package com.infopolis.infopolis.data.remote.response

data class CityScoreInfo(
    val _links: CityScoreInfoLinks? = null,
    val categories: List<ScoreCategory>? = null,
    val summary: String = "",
    val teleport_city_score: Double? = null
)