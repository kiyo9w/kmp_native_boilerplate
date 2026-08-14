package dev.kiyo9w.kmpboilerplate.data.session

import com.russhwolf.settings.Settings
import dev.kiyo9w.kmpboilerplate.domain.session.SessionSnapshot
import dev.kiyo9w.kmpboilerplate.domain.session.SessionStore

class SettingsSessionStore(
    private val settings: Settings,
) : SessionStore {
    override fun snapshot(): SessionSnapshot = SessionSnapshot(
        launchCount = settings.getInt(KEY_LAUNCH_COUNT, 0),
        lastOpenedEpochMs = settings.getLong(KEY_LAST_OPENED, 0L),
        lastRoute = settings.getString(KEY_LAST_ROUTE, ROUTE_CATALOG),
    )

    override fun recordLaunch(nowEpochMs: Long): SessionSnapshot {
        val next = snapshot().launchCount + 1
        settings.putInt(KEY_LAUNCH_COUNT, next)
        settings.putLong(KEY_LAST_OPENED, nowEpochMs)
        return snapshot().copy(launchCount = next, lastOpenedEpochMs = nowEpochMs)
    }

    override fun setLastRoute(route: String) {
        settings.putString(KEY_LAST_ROUTE, route)
    }

    private companion object {
        const val KEY_LAUNCH_COUNT = "session.launch_count"
        const val KEY_LAST_OPENED = "session.last_opened_epoch_ms"
        const val KEY_LAST_ROUTE = "session.last_route"
        const val ROUTE_CATALOG = "catalog"
    }
}
