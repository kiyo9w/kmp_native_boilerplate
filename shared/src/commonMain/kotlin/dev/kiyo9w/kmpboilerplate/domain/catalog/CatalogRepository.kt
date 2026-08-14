package dev.kiyo9w.kmpboilerplate.domain.catalog

import dev.kiyo9w.kmpboilerplate.core.AppResult
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun observeItems(): Flow<List<CatalogItem>>
    fun observeItem(id: Long): Flow<CatalogItem?>
    suspend fun refresh(): AppResult<List<CatalogItem>>
}
