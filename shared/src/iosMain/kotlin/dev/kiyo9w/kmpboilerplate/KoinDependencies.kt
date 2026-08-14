package dev.kiyo9w.kmpboilerplate

import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinDependencies : KoinComponent {
    val catalogRepository: CatalogRepository by inject()
}
