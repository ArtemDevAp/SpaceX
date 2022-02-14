package com.artem.mi.spacexautenticom

import com.artem.mi.spacexautenticom.di.ApiModule
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class]
)
class TestRetrofitModule : ApiModule() {

    override val provideSpaceXUrl: String
        get() = "http://127.0.0.1:8080"
}