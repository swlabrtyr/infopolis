package com.infopolis.infopolis.data.remote.response

data class CitySearchNameResult(
    val _links: CitySearchNameResultLinks? = null,
    val matching_alternate_names: List<MatchingAlternateName>? = null,
    val matching_full_name: String
)