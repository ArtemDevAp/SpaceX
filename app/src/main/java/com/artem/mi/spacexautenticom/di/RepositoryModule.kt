package com.artem.mi.spacexautenticom.di

import com.artem.mi.spacexautenticom.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.cache.LaunchpadDetailCache
import com.artem.mi.spacexautenticom.network.ISpaceXLaunchpadClient
import com.artem.mi.spacexautenticom.repository.LaunchpadRepo
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(subcomponents = [])
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLaunchpadRepo(
        launchpadCache: LaunchpadCache,
        launchpadDetailCache: LaunchpadDetailCache,
        apiClient: ISpaceXLaunchpadClient,
        moshi: Moshi
    ): LaunchpadRepo = LaunchpadRepo(
        launchpadCache = launchpadCache,
        launchpadDetailCache = launchpadDetailCache,
        iSpaceXClient = apiClient,
        moshi = moshi
    )

}
