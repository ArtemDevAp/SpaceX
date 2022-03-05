package com.artem.mi.spacexautenticom.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.IdlingRegistry
import com.artem.mi.spacexautenticom.FileReader
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.*
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.runners.MethodSorters
import java.io.EOFException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
class LaunchpadRepoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var launchpadRepo: LaunchpadRepo

    @Inject
    lateinit var okHttpClient: OkHttpClient

    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        hiltRule.inject()

        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(
            OkHttp3IdlingResource.create(
                "okhttp",
                okHttpClient
            )
        )
    }

    @Test
    fun testFetchLaunchpads() = runBlocking {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readStringFile())
            }
        }

        assertTrue(launchpadRepo.fetchLaunchpads().isNotEmpty())
    }

    @Test
    fun testFailFetchLaunchpads() = runBlocking {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse().throttleBody(1024, 5, TimeUnit.SECONDS)
            }
        }

        val exception: Exception = assertThrows(EOFException::class.java) {
            runBlocking {
                launchpadRepo.fetchLaunchpads()
            }
        }

        val expectedMessage = "End of input"
        val actualMessage = exception.message

        assertTrue(expectedMessage == actualMessage)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}