package com.artem.mi.spacexautenticom.di

import com.artem.mi.spacexautenticom.network.SpaceXApiClient
import com.artem.mi.spacexautenticom.network.ISpaceXClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideApiClient(moshi: Moshi): SpaceXApiClient = SpaceXApiClient(moshi)

    @Provides
    @Singleton
    fun provideRetrofit(apiClient: SpaceXApiClient): Retrofit = apiClient.provideRetrofit()

    @Provides
    @Singleton
    fun provideSpaceXClient(retrofit: Retrofit): ISpaceXClient =
        retrofit.create(ISpaceXClient::class.java)
}