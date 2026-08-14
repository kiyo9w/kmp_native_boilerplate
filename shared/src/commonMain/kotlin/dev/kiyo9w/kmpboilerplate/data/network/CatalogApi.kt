package dev.kiyo9w.kmpboilerplate.data.network

import dev.kiyo9w.kmpboilerplate.core.AppConfig
import dev.kiyo9w.kmpboilerplate.core.AppLog
import dev.kiyo9w.kmpboilerplate.core.AppResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.serialization.Serializable

interface CatalogApi {
    suspend fun fetchImageUrls(): AppResult<List<String>>
}

@Serializable
internal data class DogCeoResponse(
    val message: List<String> = emptyList(),
    val status: String = "",
)

class KtorCatalogApi(
    private val client: HttpClient,
    private val baseUrl: String = AppConfig.catalogBaseUrl,
    private val pageSize: Int = AppConfig.catalogPageSize,
) : CatalogApi {
    override suspend fun fetchImageUrls(): AppResult<List<String>> {
        return try {
            val response: DogCeoResponse = client.get("$baseUrl/breeds/image/random/$pageSize").body()
            if (response.status != "success") {
                AppResult.Err("catalog api status=${response.status}")
            } else {
                AppResult.Ok(response.message)
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            AppLog.e("catalog refresh failed", e)
            AppResult.Err("catalog refresh failed", e)
        }
    }
}
