package com.example.hpapiapp.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpClient {
    suspend fun get(url: String): String = withContext(Dispatchers.IO) {
        val connection = (URL(url).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 5000
            readTimeout = 5000
        }
        try {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val result = reader.use(BufferedReader::readText)
            result
        } finally {
            connection.disconnect()
        }
    }
}