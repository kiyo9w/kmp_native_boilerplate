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
```

## Layers

| Layer | Package | Allowed to know |
| --- | --- | --- |
| core | `core/` | logging, result, config, crash hook |
| domain | `domain/` | models and repository interfaces |
| data | `data/` | Ktor, SQLDelight, Settings, repository impls |
| feature | `feature/` | ViewModels that expose `StateFlow` |
| app | `androidApp/`, `iosApp/` | theme, routing, widgets |

A ViewModel talks to a domain interface. It does not construct an `HttpClient`.

## Adding a feature

1. Model + repository interface in `domain/<name>/`.
2. API / cache / impl in `data/`.
3. Bind in `di/Koin.kt`.
4. ViewModel in `feature/<name>/` with `@NativeCoroutinesState` on every Flow SwiftUI reads.
5. Compose screen in `androidApp/.../screens/`.
6. SwiftUI view in `iosApp/iosApp/`.

## Navigation

Android uses Navigation Compose type-safe routes. iOS uses `NavigationStack`. The shared module does not own a back stack. That keeps each UI writable by a separate agent.

## Persistence

- SQLDelight: typed cache (`AppDatabase`, `Catalog.sq`)
- multiplatform-settings: launch count and last-opened time
- Drivers: `AndroidDatabaseDriverFactory` and `IosDatabaseDriverFactory`

Tests use `InMemoryCatalogCache` so common tests do not need SQLite natives.

## Session

`initKoin` records one launch through `SessionStore`. Read `SessionStore.snapshot()` from a ViewModel when a screen needs a quiet “days opened” number.

## Flavors

`AppConfig` is the flavor stand-in. Add Gradle product flavors and Xcode schemes when you have real debug/staging/prod URLs. Do not add BuildKonfig until a second environment exists.
