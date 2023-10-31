@file:Suppress("UnstableApiUsage")

import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("androidx.navigation.safeargs")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.ksp)
}

val secretPropertiesFile = rootProject.file("secret.properties")
val secretProperties = Properties()
secretProperties.load(secretPropertiesFile.inputStream())

android {
    namespace = "com.artem.mi.spacexautenticom"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.artem.mi.spacexautenticom"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.artem.mi.spacexautenticom.HiltTestRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            manifestPlaceholders["usesCleartextTraffic"] = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            applicationIdSuffix = ".release"
            manifestPlaceholders["usesCleartextTraffic"] = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)

    // testing
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.rules)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.core.testing)
    androidTestImplementation(libs.junit.ext)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)

    // mock server
    androidTestImplementation(libs.mockwebserver)

    // retrofit 2
    implementation(libs.bundles.retrofit)

    // view
    implementation(libs.constraintlayout)
    implementation(libs.material)

    // lifecycle
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // androidX
    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)

    // OkHttp 3
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    androidTestImplementation(libs.okhttp3.idling.resource)

    // recycler
    implementation(libs.recyclerview)

    // navigation ui
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.testing)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

    // kotlin serialization
    implementation(libs.kotlinx.serialization.json)
}