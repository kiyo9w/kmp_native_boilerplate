package dev.kiyo9w.kmpboilerplate.di

import com.russhwolf.settings.Settings
import dev.kiyo9w.kmpboilerplate.core.CrashReporter
import dev.kiyo9w.kmpboilerplate.core.Flavor
import dev.kiyo9w.kmpboilerplate.core.NoOpCrashReporter
import dev.kiyo9w.kmpboilerplate.data.catalog.CatalogRepositoryImpl
import dev.kiyo9w.kmpboilerplate.data.local.AppDatabase
import dev.kiyo9w.kmpboilerplate.data.local.CatalogCache
import dev.kiyo9w.kmpboilerplate.data.local.DatabaseDriverFactory
import dev.kiyo9w.kmpboilerplate.data.local.SqlDelightCatalogCache
import dev.kiyo9w.kmpboilerplate.data.network.CatalogApi
import dev.kiyo9w.kmpboilerplate.data.network.HttpClientFactory
import dev.kiyo9w.kmpboilerplate.data.network.KtorCatalogApi
import dev.kiyo9w.kmpboilerplate.data.session.SettingsSessionStore
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogRepository
import dev.kiyo9w.kmpboilerplate.domain.session.SessionStore
import dev.kiyo9w.kmpboilerplate.feature.catalog.CatalogDetailViewModel
import dev.kiyo9w.kmpboilerplate.feature.catalog.CatalogListViewModel
import dev.kiyo9w.kmpboilerplate.platform.AppVersionReader
import kotlin.time.Clock
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun coreModule(
    driverFactory: DatabaseDriverFactory,
    flavor: Flavor,
    crashReporter: CrashReporter,
    appVersionReader: AppVersionReader,
): Module = module {
    single { flavor }
    single<CrashReporter> { crashReporter }
    single<AppVersionReader> { appVersionReader }
    single<DatabaseDriverFactory> { driverFactory }
    single { Settings() }
    single<SessionStore> { SettingsSessionStore(get()) }
    single { HttpClientFactory.create() }
    single { AppDatabase(get<DatabaseDriverFactory>().createDriver()) }
}

/**
 * Copy this module when adding a feature: API, cache, repository, ViewModels.
 */
fun catalogModule(): Module = module {
    single<CatalogApi> { KtorCatalogApi(client = get(), baseUrl = get<Flavor>().catalogBaseUrl) }
    single<CatalogCache> { SqlDelightCatalogCache(get()) }
    single<CatalogRepository> { CatalogRepositoryImpl(get(), get(), get()) }
    factory { CatalogListViewModel(get(), get(), get()) }
    factory { CatalogDetailViewModel(get(), get()) }
}

fun initKoin(
    driverFactory: DatabaseDriverFactory,
    flavor: Flavor = Flavor.Debug,
    crashReporter: CrashReporter = NoOpCrashReporter(),
    appVersionReader: AppVersionReader,
    extraModules: List<Module> = emptyList(),
) {
    val koin = startKoin {
        modules(
            coreModule(driverFactory, flavor, crashReporter, appVersionReader),
            catalogModule(),
            *extraModules.toTypedArray(),
        )
    }.koin
    koin.get<SessionStore>().recordLaunch(Clock.System.now().toEpochMilliseconds())
}
