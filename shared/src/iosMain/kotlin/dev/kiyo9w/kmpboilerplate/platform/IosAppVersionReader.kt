package dev.kiyo9w.kmpboilerplate.platform

import platform.Foundation.NSBundle

/**
 * Official iOS API: [NSBundle.objectForInfoDictionaryKey] for
 * CFBundleShortVersionString and CFBundleVersion.
 * Source: https://developer.apple.com/documentation/foundation/bundle
 */
class IosAppVersionReader : AppVersionReader {
    override fun current(): AppVersion {
        val bundle = NSBundle.mainBundle
        val name = bundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "0"
        val build = bundle.objectForInfoDictionaryKey("CFBundleVersion") as? String ?: "0"
        return AppVersion(name = name, build = build)
    }
}
