package dev.kiyo9w.kmpboilerplate.data.session

import com.russhwolf.settings.MapSettings
import kotlin.test.Test
import kotlin.test.assertEquals

class SettingsSessionStoreTest {
    @Test
    fun recordLaunch_incrementsCount() {
        val store = SettingsSessionStore(MapSettings())
        store.recordLaunch(1_000L)
        val after = store.recordLaunch(2_000L)
        assertEquals(2, after.launchCount)
        assertEquals(2_000L, after.lastOpenedEpochMs)
    }

    @Test
    fun setLastRoute_persistsProductRoute() {
        val store = SettingsSessionStore(MapSettings())
        store.setLastRoute("catalog/42")
        assertEquals("catalog/42", store.snapshot().lastRoute)
    }
}
