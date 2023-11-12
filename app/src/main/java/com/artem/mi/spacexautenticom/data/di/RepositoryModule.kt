package com.artem.mi.spacexautenticom.data.di

import com.artem.mi.spacexautenticom.data.LaunchpadRepositoryImpl
import com.artem.mi.spacexautenticom.domain.launchpads.LaunchpadRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideLaunchpadRepo(launchpadRepository: LaunchpadRepositoryImpl): LaunchpadRepository
}