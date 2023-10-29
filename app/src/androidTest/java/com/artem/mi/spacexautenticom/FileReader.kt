package com.artem.mi.spacexautenticom

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import java.io.BufferedReader
import java.io.IOException

object FileReader {

    fun readStringFile(): String {
        try {
            return getInstrumentation().targetContext.resources.openRawResource(R.raw.success_response)
                .bufferedReader().use(BufferedReader::readText)
        } catch (e: IOException) {
            throw e
        }
    }
}