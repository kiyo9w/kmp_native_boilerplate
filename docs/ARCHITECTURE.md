# Architecture

Shared Kotlin owns rules, IO, and screen state. Each OS owns pixels and navigation.

```
iosApp (SwiftUI)          androidApp (Compose)
        \                    /
         \                  /
          shared feature ViewModels
                    |
              domain interfaces
                    |
     data (Ktor, SQLDelight, Settings)
                    |
            platform (device APIs)
```

## Layers

| Layer | Package | Allowed to know |
| --- | --- | --- |
| core | `core/` | result, flavor, crash hook, Kermit |
| platform | `platform/` | one device job, official Android + official iOS |
| domain | `domain/` | models and repository interfaces |
| data | `data/` | Ktor, SQLDelight, Settings, repository impls |
| feature | `feature/` | ViewModels that expose `StateFlow` |
| app | `androidApp/`, `iosApp/` | theme, routing, widgets, string catalogs |

A ViewModel talks to a domain interface and `AppVersionReader`. Koin supplies HTTP, cache, and reporters. `HttpClientFactory` installs JSON plus 15s request timeouts.

## Adding a feature

1. Model + repository interface in `domain/<name>/`.
2. API / cache / impl in `data/`.
3. Bind a copy of `catalogModule()` in `di/Koin.kt`.
4. ViewModel in `feature/<name>/` with `@NativeCoroutinesState` on every Flow SwiftUI reads.
5. Compose screen in `androidApp/.../screens/`.
6. SwiftUI view in `iosApp/iosApp/`.

`initKoin` loads `coreModule` then `catalogModule`, then any app `extraModules`. Pass `CrashReporter` and `AppVersionReader` from each app. The default reporter is `NoOpCrashReporter`. Swap that argument for a Crashlytics or Sentry wrapper when those SDKs exist. Keep the wrapper in the app module so the shared module stays free of vendor SDKs.

## Navigation

Android uses Navigation Compose type-safe routes in `App.kt`. iOS uses `NavigationStack` in `ListView.swift`. The shared module stores `SessionStore.lastRoute` (`catalog`, `catalog/{id}`). It does not own a back stack. That keeps each UI writable by a separate agent.

## Persistence

- SQLDelight: typed cache (`AppDatabase`, `Catalog.sq`)
- multiplatform-settings: launch count, last-opened time, last route
- Drivers: `AndroidDatabaseDriverFactory` and `IosDatabaseDriverFactory`
- iOS links system SQLite with `OTHER_LDFLAGS = -lsqlite3` (SQLDelight native driver)

`commonTest` uses `InMemoryCatalogCache`, so iOS test compile stays free of SQLite natives. `jvmTest` uses `JdbcSqliteDriver` and the real schema (`SqlDelightCatalogCacheTest`). Source: https://sqldelight.github.io/sqldelight/2.1.0/jvm_sqlite/

## Session

`initKoin` records one launch through `SessionStore`. List and detail ViewModels write the last route. Read `SessionStore.snapshot()` when a screen needs launch count or a restore hint.

## Flavors

`Flavor.Debug`, `Flavor.Staging`, and `Flavor.Prod` live in shared code. Today they share `https://dog.ceo/api`. Each app selects one at `initKoin`:

- Android `BuildConfig.APP_ENVIRONMENT` from `app.environment` (debug default `debug`, release default `prod`)
- iOS `APP_ENVIRONMENT` in `Config.xcconfig`, copied into `Info.plist`

Add BuildKonfig when a second base URL is real. Gradle product flavors that rename `assembleDebug` stay out until that day.

## Theme

`ThemeTokens` in shared Kotlin is the spacing, radius, duration, and tap-size scale. Android maps it in `ui/ThemeTokens.android.kt`. iOS maps it in `KitTheme.swift`. Colors stay on Material3 and SwiftUI semantic colors.

Both UIs follow the system light/dark setting.

- Android: `Theme.KmpBoilerplate` (`values` light / `values-night` dark) plus Material3 `isSystemInDarkTheme()`
- iOS: SwiftUI default (`preferredColorScheme(nil)`)

Tokens are Material3 color scheme on Android and SwiftUI semantic colors on iOS. There is no shared color XML. A product that needs a locked light theme sets an explicit scheme in each app and writes that lock here.

## i18n

Chrome copy lives in native string catalogs. English is the source. Vietnamese is the second locale (`values-vi/strings.xml`, `Localizable.xcstrings`). Shared code stays language-agnostic. Add moko-resources when a product needs the same sentence in shared Kotlin.

## Native device API

`platform/AppVersion` is the template.

| Side | Type | Official API |
| --- | --- | --- |
| Shared | `AppVersionReader` | interface only |
| Android | `AndroidAppVersionReader` | `PackageManager.getPackageInfo` |
| iOS | `IosAppVersionReader` | `NSBundle` `CFBundleShortVersionString` / `CFBundleVersion` |

Sources: https://developer.android.com/reference/android/content/pm/PackageManager and https://developer.apple.com/documentation/foundation/bundle

Copy that package for the next phone job. Prefer a thin shared interface plus two native classes when Android needs `Context`. Use `expect`/`actual` when both sides are parameterless (locale tag, debug flag).

Koin setup follows https://insert-koin.io/docs/reference/koin-core/kmp-setup: shared modules in `commonMain`, platform types passed in from each app.
