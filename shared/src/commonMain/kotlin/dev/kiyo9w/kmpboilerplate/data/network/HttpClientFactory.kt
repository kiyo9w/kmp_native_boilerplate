package dev.kiyo9w.kmpboilerplate.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(): HttpClient {
        val json = Json { ignoreUnknownKeys = true }
        return HttpClient {
            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
    }
}
