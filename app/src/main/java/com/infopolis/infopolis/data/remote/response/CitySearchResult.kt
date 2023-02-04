package com.infopolis.infopolis.data.remote.response

data class CitySearchResult(
    val _embedded: Embedded,
    val _links: SearchResultLinks,
    val count: Int
)