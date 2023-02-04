package com.infopolis.infopolis.data.remote.response

data class Score(
    val _links: Links,
    val categories: List<Category>,
    val summary: String,
    val teleport_city_score: Double
)