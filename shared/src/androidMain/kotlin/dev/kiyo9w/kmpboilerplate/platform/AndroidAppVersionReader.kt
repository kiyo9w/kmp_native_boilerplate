package dev.kiyo9w.kmpboilerplate.platform

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Official Android API: [PackageManager.getPackageInfo].
 * Source: https://developer.android.com/reference/android/content/pm/PackageManager
 */
class AndroidAppVersionReader(
    private val context: Context,
) : AppVersionReader {
    override fun current(): AppVersion {
        val packageManager = context.packageManager
        val packageName = context.packageName
        val info = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, 0)
        }
        val name = info.versionName ?: "0"
        val build = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            info.longVersionCode.toString()
        } else {
            @Suppress("DEPRECATION")
            info.versionCode.toString()
        }
        return AppVersion(name = name, build = build)
    }
}
