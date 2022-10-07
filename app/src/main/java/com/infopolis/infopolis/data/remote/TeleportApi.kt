package com.infopolis.infopolis.data.remote

import com.infopolis.infopolis.data.remote.response.CityImageInfo
import com.infopolis.infopolis.data.remote.response.CityScoreInfo
import com.infopolis.infopolis.data.remote.response.CitySearchResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TeleportApi {

    @GET("cities/")
    suspend fun getCity(
        @Query("search") city: String
    ): CitySearchResult

    @GET("urban_areas/slug:{city}/images")
    suspend fun getImageUrl(
        @Path("city") city: String?
    ): CityImageInfo

    @GET("urban_areas/slug:{city}/scores")
    suspend fun getCityInfo(
        @Path("city") city: String?
    ): CityScoreInfo
}
