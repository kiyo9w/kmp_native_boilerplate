package dev.kiyo9w.kmpboilerplate.data.catalog

import dev.kiyo9w.kmpboilerplate.core.AppLog
import dev.kiyo9w.kmpboilerplate.core.AppResult
import dev.kiyo9w.kmpboilerplate.core.CrashReporter
import dev.kiyo9w.kmpboilerplate.data.local.CatalogCache
import dev.kiyo9w.kmpboilerplate.data.network.CatalogApi
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogItem
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogRepository
import kotlinx.coroutines.flow.Flow

class CatalogRepositoryImpl(
    private val api: CatalogApi,
    private val cache: CatalogCache,
    private val crashReporter: CrashReporter,
) : CatalogRepository {
    override fun observeItems(): Flow<List<CatalogItem>> = cache.observeItems()

    override fun observeItem(id: Long): Flow<CatalogItem?> = cache.observeItem(id)

    override suspend fun refresh(): AppResult<List<CatalogItem>> {
        return when (val result = api.fetchImageUrls()) {
            is AppResult.Err -> {
                crashReporter.record(result.cause ?: Exception(result.message))
                result
            }
            is AppResult.Ok -> {
                val items = result.value.map(CatalogItem::fromDogImageUrl)
                cache.replaceAll(items)
                AppLog.i("catalog cached ${items.size} items")
                AppResult.Ok(items)
            }
        }
    }
}
