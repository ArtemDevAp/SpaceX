package com.artem.mi.spacexautenticom.di

import com.artem.mi.spacexautenticom.network.ConnectivityService
import com.artem.mi.spacexautenticom.network.ConnectivityServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {

    @Binds
    @Singleton
    fun bindConnectivityService(connectivityServiceImpl: ConnectivityServiceImpl): ConnectivityService
}