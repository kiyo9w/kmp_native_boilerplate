package dev.kiyo9w.kmpboilerplate.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogItem as DomainCatalogItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface CatalogCache {
    fun observeItems(): Flow<List<DomainCatalogItem>>
    fun observeItem(id: Long): Flow<DomainCatalogItem?>
    suspend fun replaceAll(items: List<DomainCatalogItem>)
}

class InMemoryCatalogCache : CatalogCache {
    private val items = MutableStateFlow<List<DomainCatalogItem>>(emptyList())

    override fun observeItems(): Flow<List<DomainCatalogItem>> = items

    override fun observeItem(id: Long): Flow<DomainCatalogItem?> =
        items.map { list -> list.find { it.id == id } }

    override suspend fun replaceAll(items: List<DomainCatalogItem>) {
        this.items.value = items
    }
}

class SqlDelightCatalogCache(
    private val database: AppDatabase,
) : CatalogCache {
    private val queries get() = database.catalogQueries

    override fun observeItems(): Flow<List<DomainCatalogItem>> =
        queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { rows -> rows.map { it.toDomain() } }

    override fun observeItem(id: Long): Flow<DomainCatalogItem?> =
        queries.selectById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { row -> row?.toDomain() }

    override suspend fun replaceAll(items: List<DomainCatalogItem>) = withContext(Dispatchers.IO) {
        database.transaction {
            queries.deleteAll()
            items.forEach { item ->
                queries.insert(item.id, item.imageUrl, item.breed, item.source)
            }
        }
    }
}

private fun CatalogItem.toDomain(): DomainCatalogItem =
    DomainCatalogItem(id = id, imageUrl = imageUrl, breed = breed, source = source)
