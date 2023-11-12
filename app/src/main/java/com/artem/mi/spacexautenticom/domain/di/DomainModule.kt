package com.artem.mi.spacexautenticom.domain.di

import com.artem.mi.spacexautenticom.domain.launchpads.FetchLaunchpadsUseCase
import com.artem.mi.spacexautenticom.domain.launchpads.FetchLaunchpadsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    @Singleton
    fun bindFetchLaunchpadUseCase(fetchLaunchpadsUseCaseImpl: FetchLaunchpadsUseCaseImpl): FetchLaunchpadsUseCase
}