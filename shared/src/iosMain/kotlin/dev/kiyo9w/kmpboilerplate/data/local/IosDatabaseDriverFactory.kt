package dev.kiyo9w.kmpboilerplate.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import dev.kiyo9w.kmpboilerplate.core.AppConfig

class IosDatabaseDriverFactory : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver =
        NativeSqliteDriver(AppDatabase.Schema, AppConfig.databaseName)
}
