package dev.kiyo9w.kmpboilerplate

import dev.kiyo9w.kmpboilerplate.core.CrashReporter
import dev.kiyo9w.kmpboilerplate.core.Flavor
import dev.kiyo9w.kmpboilerplate.core.NoOpCrashReporter
import dev.kiyo9w.kmpboilerplate.data.local.IosDatabaseDriverFactory
import dev.kiyo9w.kmpboilerplate.di.initKoin
import dev.kiyo9w.kmpboilerplate.domain.catalog.CatalogRepository
import dev.kiyo9w.kmpboilerplate.domain.session.SessionStore
import dev.kiyo9w.kmpboilerplate.platform.AppVersionReader
import dev.kiyo9w.kmpboilerplate.platform.IosAppVersionReader
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun initKoinIos(flavorName: String) {
    initKoinIos(flavorName, NoOpCrashReporter())
}

fun initKoinIos(
    flavorName: String,
    crashReporter: CrashReporter,
) {
    initKoin(
        driverFactory = IosDatabaseDriverFactory(),
        flavor = Flavor.fromName(flavorName),
        crashReporter = crashReporter,
        appVersionReader = IosAppVersionReader(),
    )
}

class KoinDependencies : KoinComponent {
    val catalogRepository: CatalogRepository by inject()
    val sessionStore: SessionStore by inject()
    val appVersionReader: AppVersionReader by inject()
}
