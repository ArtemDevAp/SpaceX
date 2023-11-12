package com.artem.mi.spacexautenticom.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    private companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/"
    }

    open fun provideSpaceXUrl(baseUrl: String = BASE_URL): String = baseUrl

    @Provides
    @Singleton
    fun provideJson() = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideFactory(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .connectTimeout(10000, TimeUnit.MILLISECONDS)
        .readTimeout(10000, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(factory: Converter.Factory, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(factory)
            .baseUrl(provideSpaceXUrl())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideSpaceXClient(retrofit: Retrofit): SPXLaunchpadClient =
        retrofit.create(SPXLaunchpadClient::class.java)
}