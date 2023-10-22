package com.artem.mi.spacexautenticom.ui.launchpadDetail.di

import com.artem.mi.spacexautenticom.ui.launchpadDetail.LaunchpadDetailUiMapper
import com.artem.mi.spacexautenticom.ui.launchpadDetail.LaunchpadDetailUiMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface LaunchpadDetailUiModule {

    @Binds
    @ViewModelScoped
    fun bindDetailLaunchpadMapper(uiMapper: LaunchpadDetailUiMapperImpl): LaunchpadDetailUiMapper
}