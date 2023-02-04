package com.infopolis.infopolis.di

import com.infopolis.infopolis.data.remote.TeleportApi
import com.infopolis.infopolis.repository.CityInfoRepository
import com.infopolis.infopolis.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCityInfoRepository(
        api: TeleportApi
    ) = CityInfoRepository(api)

    @Singleton
    @Provides
    fun provideTeleportApi(): TeleportApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .build()
            .create(TeleportApi::class.java)
    }
}