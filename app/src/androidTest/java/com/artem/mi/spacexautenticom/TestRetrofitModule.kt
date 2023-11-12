package com.artem.mi.spacexautenticom

import com.artem.mi.spacexautenticom.data.network.NetworkModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class TestRetrofitModule : NetworkModule() {

    override fun provideSpaceXUrl(baseUrl: String): String {
        return "http://127.0.0.1:8080"
    }
}