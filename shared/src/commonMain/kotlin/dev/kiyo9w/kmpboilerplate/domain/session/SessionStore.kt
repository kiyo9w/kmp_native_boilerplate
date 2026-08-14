package dev.kiyo9w.kmpboilerplate.domain.session

data class SessionSnapshot(
    val launchCount: Int,
    val lastOpenedEpochMs: Long,
    val lastRoute: String,
)

interface SessionStore {
    fun snapshot(): SessionSnapshot
    fun recordLaunch(nowEpochMs: Long): SessionSnapshot
    fun setLastRoute(route: String)
}
