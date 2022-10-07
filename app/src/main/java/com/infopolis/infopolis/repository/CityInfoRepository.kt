package com.infopolis.infopolis.repository

import com.infopolis.infopolis.data.remote.TeleportApi
import com.infopolis.infopolis.data.remote.response.CityImageInfo
import com.infopolis.infopolis.data.remote.response.CityScoreInfo
import com.infopolis.infopolis.data.remote.response.CitySearchResult
import com.infopolis.infopolis.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CityInfoRepository @Inject constructor(
    private val api: TeleportApi
) {
    suspend fun getCities(city: String): Resource<CitySearchResult> {
        val response = try {
            api.getCity(city = city)
        } catch (e: Exception) {
            return Resource.Failure(message = "Error occurred $e")
        }
        return Resource.Success(response)
    }

    suspend fun getCityImage(city: String?): Resource<CityImageInfo> {
        val response = try {
            api.getImageUrl(city = city)
        } catch (e: Exception) {
            return Resource.Failure(message = "Error occurred $e")
        }
        return Resource.Success(response)
    }

    suspend fun getCityScoreInfo(city: String?): Resource<CityScoreInfo> {
        val response = try {
            api.getCityInfo(city = city)
        } catch (e: Exception) {
            return Resource.Failure(message = "Error occurred $e")
        }
        return Resource.Success(response)
    }
}