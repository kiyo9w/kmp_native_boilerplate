# kmp_native_boilerplate

Kotlin Multiplatform starter with one shared Kotlin core and two native UIs:

- Android: Jetpack Compose
- iOS: SwiftUI

It is the KMP cousin of [zeref278/flutter_boilerplate](https://github.com/zeref278/flutter_boilerplate): clean layers, typed API models, local cache, settings, DI, logging, flavors, tests, and CI. Versions follow current official KMP (Kotlin 2.4, Ktor 3, Koin 4, SQLDelight 2.3).

This repo is a new project, not a GitHub fork. It was cloned from [`Kotlin/KMP-App-Template-Native`](https://github.com/Kotlin/KMP-App-Template-Native) and rewritten. See `NOTICE`.

[![Build Android](https://github.com/kiyo9w/kmp_native_boilerplate/actions/workflows/build-android.yml/badge.svg)](https://github.com/kiyo9w/kmp_native_boilerplate/actions/workflows/build-android.yml)
[![Build iOS](https://github.com/kiyo9w/kmp_native_boilerplate/actions/workflows/build-ios.yml/badge.svg)](https://github.com/kiyo9w/kmp_native_boilerplate/actions/workflows/build-ios.yml)

## Who it is for

An agent or person starting a native-UI KMP product. SwiftUI in `iosApp/` and Compose in `androidApp/` share the same ViewModels. Navigation stays native on purpose.

## Layout

```
shared/                 Kotlin core (domain, data, feature ViewModels, DI, platform)
  src/commonMain/
  src/commonTest/
  src/jvmTest/          SQLDelight schema tests (no iOS sqlite natives)
  src/androidMain/
  src/iosMain/
androidApp/             Jetpack Compose
iosApp/                 SwiftUI + Xcode project
docs/                   architecture, OSS map, adopt recipe
```

Sample feature: fetch twelve dog photos from dog.ceo, map them to `CatalogItem`, cache in SQLDelight, show a grid and a detail screen. Pull to refresh on both platforms. Delete it in four steps (`docs/ADOPT.md`).

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
| App version | `PackageManager` + `NSBundle` |
| Crash reporting | `CrashReporter` no-op, bind Sentry or Crashlytics from each app |

## Requirements

- JDK 21
- Android Studio / IntelliJ with the KMP plugin
- Xcode 16+ for the iOS app
- A real Android SDK path in `local.properties` (`sdk.dir`)

## Run

If `~/.gradle` points at an unmounted volume, export a working home first:

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
```

Android:

```
./gradlew :androidApp:installDebug
```

On this machine `sdk.dir=/Users/ngotrung/Library/Android/sdk` is a symlink to `/Volumes/FreeSpace/Android/sdk/sdk`. When that volume is unmounted, `:androidApp:assembleDebug` fails with `SDK location not found`. CI still builds Android.

iOS: open `iosApp/iosApp.xcodeproj` and run the `iosApp` scheme. First Gradle sync builds the `Shared` framework.

Command-line iOS compile (no simulator boot):

```
cd iosApp
xcodebuild -scheme iosApp -configuration Debug \
  -sdk iphonesimulator \
  -destination 'generic/platform=iOS Simulator' \
  ARCHS=arm64 ONLY_ACTIVE_ARCH=YES \
  EXCLUDED_ARCHS=x86_64 \
  CODE_SIGNING_ALLOWED=NO
```

This machine's CoreSimulator has failed with `Cannot allocate memory`. Compile is required. A simulator crash is a named miss.

Intel Macs: add `iosX64()` in `shared/build.gradle.kts` before removing `EXCLUDED_ARCHS=x86_64`.

## Tests

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
./gradlew :shared:jvmTest :shared:compileTestKotlinIosSimulatorArm64
```

| Gate | What it proves |
| --- | --- |
| `:shared:jvmTest` | Domain, repository, session, and the real SQLDelight schema |
| `:shared:compileTestKotlinIosSimulatorArm64` | iOS test sources compile |
| `:shared:allTests` | Same suite on every configured target. Needs a healthy simulator to *run* iOS tests |

There is no instrumented UI test in the tree. GitHub Actions `assembleDebug` and the iOS `xcodebuild` compile are the end-to-end substitutes until a simulator or device run is stable.

## Replace the sample

1. Keep `core/`, `platform/`, `di/`, session, and `data/local` driver factories.
2. Delete `domain/catalog`, `data/catalog`, `data/network/CatalogApi.kt`, `feature/catalog`, and the catalog screens.
3. Add your own domain + repository + ViewModels. Copy `catalogModule()`.
4. Point SwiftUI and Compose at the new ViewModels.

Full rename and first-feature recipe: `docs/ADOPT.md`.

## Flavors

`Flavor.Debug`, `Flavor.Staging`, and `Flavor.Prod` share the dog.ceo URL.

```
./gradlew :androidApp:assembleDebug -Papp.environment=staging
```

iOS: set `APP_ENVIRONMENT` in `iosApp/Configuration/Config.xcconfig`.

## Docs

- `docs/ADOPT.md` — clone, rename, delete sample, first feature, native-bridge prompt
- `docs/CODING.md` — how to add a feature without drifting the architecture
- `docs/ARCHITECTURE.md` — layers, navigation, persistence, flavors, device APIs
- `docs/OSS.md` — Flutter-row table and every dependency
- `docs/decisions/ADR-001-native-stack.md` — why this stack stays
- `AGENTS.md` — kit locks for the next agent
- `.grok/skills/` — `add-kmp-feature`, `add-native-bridge`, `kmp-native-guard` (Claude loads the same files via `.claude/skills/` symlinks)
