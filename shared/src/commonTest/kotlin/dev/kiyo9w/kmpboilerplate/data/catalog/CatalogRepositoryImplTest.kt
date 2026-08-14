package dev.kiyo9w.kmpboilerplate.data.catalog

import dev.kiyo9w.kmpboilerplate.core.AppResult
import dev.kiyo9w.kmpboilerplate.core.NoOpCrashReporter
import dev.kiyo9w.kmpboilerplate.data.local.InMemoryCatalogCache
import dev.kiyo9w.kmpboilerplate.data.network.CatalogApi
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class CatalogRepositoryImplTest {
    @Test
    fun refresh_writesMappedItemsIntoCache() = runTest {
        val cache = InMemoryCatalogCache()
        val repo = CatalogRepositoryImpl(
            api = FakeCatalogApi(
                AppResult.Ok(
                    listOf("https://images.dog.ceo/breeds/shiba/one.jpg"),
                ),
            ),
            cache = cache,
            crashReporter = NoOpCrashReporter(),
        )

        val result = repo.refresh()
        val items = repo.observeItems().first()

        assertTrue(result is AppResult.Ok)
        assertEquals(1, items.size)
        assertEquals("shiba", items.first().breed)
    }

    @Test
    fun refresh_keepsCacheEmptyOnApiError() = runTest {
        val cache = InMemoryCatalogCache()
        val repo = CatalogRepositoryImpl(
            api = FakeCatalogApi(AppResult.Err("down")),
            cache = cache,
            crashReporter = NoOpCrashReporter(),
        )

        val result = repo.refresh()
        assertTrue(result is AppResult.Err)
        assertEquals(emptyList(), repo.observeItems().first())
    }
}

private class FakeCatalogApi(
    private val result: AppResult<List<String>>,
) : CatalogApi {
    override suspend fun fetchImageUrls(): AppResult<List<String>> = result
}
