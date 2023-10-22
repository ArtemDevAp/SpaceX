package com.artem.mi.spacexautenticom.core.repository

import com.artem.mi.spacexautenticom.core.spexception.SpaceXTestException
import com.artem.mi.spacexautenticom.model.LaunchpadData

class FakeFetchLaunchpadsRepository : MockLaunchpadRepository {

    private var launchpads: List<LaunchpadData>? = null
    private var exception: Exception? = null

    override suspend fun fetchLaunchpads(): List<LaunchpadData> {
        exception?.let {
            throw it
        }
        return launchpads ?: throw SpaceXTestException(
            """
            class: FakeFetchLaunchpadsRepository
            cause: Failure to fetchLaunchpads
            solution: call setLaunchpads before
            """.trimIndent()
        )
    }

    fun setLaunchpads(launchpadData: List<LaunchpadData>) {
        launchpads = launchpadData
    }

    fun setError(
        exception: Exception
    ) {
        this.exception = exception
    }
}