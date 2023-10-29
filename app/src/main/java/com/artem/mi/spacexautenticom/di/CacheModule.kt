package com.artem.mi.spacexautenticom.di

import com.artem.mi.spacexautenticom.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.cache.LaunchpadDetailCache
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