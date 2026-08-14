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
    )

    override fun recordLaunch(nowEpochMs: Long): SessionSnapshot {
        val next = snapshot().launchCount + 1
        settings.putInt(KEY_LAUNCH_COUNT, next)
        settings.putLong(KEY_LAST_OPENED, nowEpochMs)
        return SessionSnapshot(launchCount = next, lastOpenedEpochMs = nowEpochMs)
    }

    private companion object {
        const val KEY_LAUNCH_COUNT = "session.launch_count"
        const val KEY_LAST_OPENED = "session.last_opened_epoch_ms"
    }
}
