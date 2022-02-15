package com.artem.mi.spacexautenticom

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import java.io.IOException
import java.io.InputStreamReader

object FileReader {

    fun readStringFile(fileName: String): String {
        try {
            val inputStream = getInstrumentation().targetContext.resources.assets.open(fileName)
            return StringBuilder().apply {
                InputStreamReader(inputStream, "UTF-8").use {
                    it.readLines().forEach { data ->
                        append(data)
                    }
                }
            }.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}