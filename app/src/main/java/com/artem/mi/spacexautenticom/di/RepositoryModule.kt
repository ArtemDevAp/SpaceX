package com.artem.mi.spacexautenticom.di

import com.artem.mi.spacexautenticom.repository.LaunchpadRepository
import com.artem.mi.spacexautenticom.repository.LaunchpadRepositoryImpl
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