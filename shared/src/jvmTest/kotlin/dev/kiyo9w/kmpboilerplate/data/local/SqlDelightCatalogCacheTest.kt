package dev.kiyo9w.kmpboilerplate.data.local

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogItem
import java.util.Properties
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

/**
 * Exercises the real SQLDelight schema on the JVM sqlite driver.
 * commonTest stays on [InMemoryCatalogCache] so iOS tests do not need sqlite natives.
 */
class SqlDelightCatalogCacheTest {
    @Test
    fun replaceAll_roundTripsThroughSchema() = runTest {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY, Properties(), AppDatabase.Schema)
        val cache = SqlDelightCatalogCache(AppDatabase(driver))
        val item = CatalogItem.fromDogImageUrl(
            "https://images.dog.ceo/breeds/shiba/one.jpg",
        )

        cache.replaceAll(listOf(item))
        val stored = cache.observeItems().first()

        assertEquals(1, stored.size)
        assertEquals(item.id, stored.first().id)
        assertEquals("shiba", stored.first().breed)
        assertEquals(item.imageUrl, stored.first().imageUrl)
    }
}
