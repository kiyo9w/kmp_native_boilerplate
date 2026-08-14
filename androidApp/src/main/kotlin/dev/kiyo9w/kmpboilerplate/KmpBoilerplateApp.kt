package dev.kiyo9w.kmpboilerplate

import android.app.Application
import dev.kiyo9w.kmpboilerplate.core.Flavor
import dev.kiyo9w.kmpboilerplate.core.NoOpCrashReporter
import dev.kiyo9w.kmpboilerplate.data.local.AndroidDatabaseDriverFactory
import dev.kiyo9w.kmpboilerplate.di.initKoin
import dev.kiyo9w.kmpboilerplate.platform.AndroidAppVersionReader

class KmpBoilerplateApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            driverFactory = AndroidDatabaseDriverFactory(this),
            flavor = Flavor.fromName(BuildConfig.APP_ENVIRONMENT),
            // Bind Crashlytics or Sentry here when those SDKs are added.
            crashReporter = NoOpCrashReporter(),
            appVersionReader = AndroidAppVersionReader(this),
        )
    }
}
