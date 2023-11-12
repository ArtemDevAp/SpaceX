package com.artem.mi.spacexautenticom.domain

import com.artem.mi.spacexautenticom.data.cache.LaunchpadCache
import com.artem.mi.spacexautenticom.core.network.FakeConnectivityService
import com.artem.mi.spacexautenticom.core.repository.FakeFetchLaunchpadsRepository
import com.artem.mi.spacexautenticom.core.spaceXTest
import com.artem.mi.spacexautenticom.core.spexception.SpaceXTestException
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import com.artem.mi.spacexautenticom.domain.launchpads.FetchLaunchpadsUseCase
import com.artem.mi.spacexautenticom.domain.launchpads.FetchLaunchpadsUseCaseImpl
import com.artem.mi.spacexautenticom.domain.launchpads.Launchpads
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.LinkedList

class FetchLaunchpadsUseCaseImplTest {

    private val connectivityService = FakeConnectivityService()
    private val launchpadRepository = FakeFetchLaunchpadsRepository()
    private val launchpadLocalCache = LaunchpadCache()

    private lateinit var useCase: FetchLaunchpadsUseCase

    private val mockLaunchpads = Launchpads(
        listOf(
            LaunchpadData(
                siteId = "ID",
                siteNameLong = "nameLong"
            ),
            LaunchpadData(
                siteId = "ID1",
                siteNameLong = "nameLong1"
            )
        )
    )

    @Before
    fun setup() {
        useCase = FetchLaunchpadsUseCaseImpl(
            connectivityService = connectivityService,
            launchpadRepository = launchpadRepository,
            launchpadLocalCache = launchpadLocalCache
        )
    }

    @Test
    fun `observe use case and given launchpads with network enabled, then expect correct state`() =
        runTest {
            val data = LinkedList<Launchpads>()
            spaceXTest(
                doOnStart = {
                    useCase.invoke.collect { data.add(it) }
                },
                given = {
                    launchpadRepository.setLaunchpads(mockLaunchpads.launchpads)
                    connectivityService.emit(true)
                },
                assert = arrayOf({ assertEquals(mockLaunchpads, data.poll()) })
            )
        }

    @Test
    fun `observe use case and given launchpads with network disabled, then expect correct state`() =
        runTest {
            val data = LinkedList<Launchpads>()
            spaceXTest(
                doOnStart = {
                    useCase.invoke.collect { data.add(it) }
                },
                given = {
                    launchpadLocalCache.addAll(mockLaunchpads.launchpads)
                    connectivityService.emit(false)
                },
                assert = arrayOf({ assertEquals(mockLaunchpads, data.poll()) })
            )
        }

    @Test(expected = SpaceXTestException::class)
    fun `give RuntimeException, when observe use case, then expect error`() = runTest {
        spaceXTest(
            doOnStart = {
                useCase.invoke.collect()
            },
            given = {
                connectivityService.emit(true)
                launchpadRepository.setError(SpaceXTestException(""))
            }
        )
    }
}