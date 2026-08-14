package dev.kiyo9w.kmpboilerplate.di

import com.russhwolf.settings.Settings
import dev.kiyo9w.kmpboilerplate.core.CrashReporter
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
import kotlin.time.Clock
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun sharedModule(driverFactory: DatabaseDriverFactory): Module = module {
    single<CrashReporter> { NoOpCrashReporter() }
    single { Settings() }
    single<SessionStore> { SettingsSessionStore(get()) }
    single { HttpClientFactory.create() }
    single<CatalogApi> { KtorCatalogApi(get()) }
    single { AppDatabase(driverFactory.createDriver()) }
    single<CatalogCache> { SqlDelightCatalogCache(get()) }
    single<CatalogRepository> { CatalogRepositoryImpl(get(), get(), get()) }
}

fun initKoin(
    driverFactory: DatabaseDriverFactory,
    extraModules: List<Module> = emptyList(),
) {
    val koin = startKoin {
        modules(sharedModule(driverFactory), *extraModules.toTypedArray())
    }.koin
    koin.get<SessionStore>().recordLaunch(Clock.System.now().toEpochMilliseconds())
}
