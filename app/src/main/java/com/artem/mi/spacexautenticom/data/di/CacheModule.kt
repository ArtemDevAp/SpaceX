package com.artem.mi.spacexautenticom.data.di

import com.artem.mi.spacexautenticom.data.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.data.cache.LaunchpadDetailCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideLaunchpadCache(): LaunchpadCache = LaunchpadCache()

    @Singleton
    @Provides
    fun provideLaunchpadDetailCache(): LaunchpadDetailCache = LaunchpadDetailCache()
}