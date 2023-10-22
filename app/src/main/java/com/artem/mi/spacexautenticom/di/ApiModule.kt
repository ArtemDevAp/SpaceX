package com.artem.mi.spacexautenticom.di

import com.artem.mi.spacexautenticom.network.ISpaceXLaunchpadClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class ApiModule {

    private companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/"
    }

    open fun provideSpaceXUrl(baseUrl: String = BASE_URL): String = baseUrl

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

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
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(provideSpaceXUrl())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideSpaceXClient(retrofit: Retrofit): ISpaceXLaunchpadClient =
        retrofit.create(ISpaceXLaunchpadClient::class.java)
}