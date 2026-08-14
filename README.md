# kmp_native_boilerplate

Kotlin Multiplatform starter with **one shared Kotlin core** and **two native UIs**:

- Android: Jetpack Compose
- iOS: SwiftUI

It is the KMP cousin of [zeref278/flutter_boilerplate](https://github.com/zeref278/flutter_boilerplate): clean layers, generated API models, local cache, settings, DI, logging, tests, and CI. The Flutter kit last moved more than a year ago. Versions here follow current official KMP (Kotlin 2.4, Ktor 3, Koin 4, SQLDelight 2.3).

This repo is **not a GitHub fork**. It was cloned from [`Kotlin/KMP-App-Template-Native`](https://github.com/Kotlin/KMP-App-Template-Native) and rewritten. See `NOTICE`.

## Why this shape

An agent can write SwiftUI in `iosApp/` and Compose in `androidApp/` against the same ViewModels. Navigation stays native on purpose. Do not switch this kit to Compose Multiplatform unless you want one shared UI again.

## Layout

```
shared/                 Kotlin core (domain, data, feature ViewModels, DI)
  src/commonMain/
  src/commonTest/
  src/androidMain/
  src/iosMain/
androidApp/             Jetpack Compose
iosApp/                 SwiftUI + Xcode project
docs/                   architecture + OSS map
```

Sample feature: fetch twelve dog photos from dog.ceo (same public-API idea as the Flutter kit), map them to `CatalogItem`, cache in SQLDelight, show a grid and a detail screen. Pull to refresh on both platforms.

## Stack

| Job | Library |
| --- | --- |
| HTTP | Ktor Client 3 |
| JSON | kotlinx.serialization |
| Local DB | SQLDelight |
| Key-value | multiplatform-settings |
| DI | Koin |
| Logging | Kermit |
| Shared ViewModel | KMP-ObservableViewModel |
| Swift Flow bridge | KMP-NativeCoroutines |
| Android images | Coil 3 |
| Time | kotlinx.datetime |

Crash reporting is a `CrashReporter` interface with a no-op. Bind Crashlytics or Sentry from each app module when you need it.

## Requirements

- JDK 21
- Android Studio / IntelliJ with the KMP plugin
- Xcode 16+ for the iOS app

## Run

Android:

```
./gradlew :androidApp:installDebug
```

Shared tests:

```
./gradlew :shared:allTests
```

iOS: open `iosApp/iosApp.xcodeproj` and run the `iosApp` scheme. First Gradle sync builds the `Shared` framework.

## Replace the sample

1. Keep `core/`, `di/`, and `data/local` driver factories.
2. Delete `domain/catalog`, `data/catalog`, `data/network/CatalogApi.kt`, and the catalog screens.
3. Add your own domain + repository + ViewModels.
4. Point SwiftUI and Compose at the new ViewModels.

## Docs

- `docs/ARCHITECTURE.md` — layers and where new code goes
- `docs/OSS.md` — why each dependency exists, and what we left out
