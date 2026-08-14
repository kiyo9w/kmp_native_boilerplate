package dev.kiyo9w.kmpboilerplate

import android.app.Application
import dev.kiyo9w.kmpboilerplate.data.local.AndroidDatabaseDriverFactory
import dev.kiyo9w.kmpboilerplate.di.initKoin
import dev.kiyo9w.kmpboilerplate.feature.catalog.CatalogDetailViewModel
import dev.kiyo9w.kmpboilerplate.feature.catalog.CatalogListViewModel
import org.koin.dsl.module

class KmpBoilerplateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            driverFactory = AndroidDatabaseDriverFactory(this),
            extraModules = listOf(
                module {
                    factory { CatalogListViewModel(get()) }
                    factory { CatalogDetailViewModel(get()) }
                },
            ),
        )
    }
}
