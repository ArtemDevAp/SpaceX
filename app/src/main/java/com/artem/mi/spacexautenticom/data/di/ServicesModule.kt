package com.artem.mi.spacexautenticom.data.di

import com.artem.mi.spacexautenticom.data.connectivity.ConnectivityServiceImpl
import com.artem.mi.spacexautenticom.domain.services.ConnectivityService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ServicesModule {

    @Binds
    @Singleton
    fun bindConnectivityService(connectivityServiceImpl: ConnectivityServiceImpl): ConnectivityService
}