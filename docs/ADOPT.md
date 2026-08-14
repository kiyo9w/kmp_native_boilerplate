# Adopt this kit

For a cold agent starting a real product from `kiyo9w/kmp_native_boilerplate`. Follow the steps in order. The sample catalog is teaching code. Keep it until the first feature compiles, then delete it.

## 1. Clone

```
git clone https://github.com/kiyo9w/kmp_native_boilerplate.git my_app
cd my_app
```

Requirements: JDK 21, Android Studio or IntelliJ with the KMP plugin, Xcode 16+ for iOS.

If `~/.gradle` is a broken symlink to an unmounted volume, use the repo Gradle home:

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
```

The iOS “Compile Kotlin Framework” phase does this automatically when `~/.gradle` is a dead link.

If `local.properties` `sdk.dir` points at a missing Android SDK, Android assemble fails with that exact path. Fix the SDK or run the iOS and JVM gates until the volume is back.

## 2. Rename

Default package and ids:

| Knob | Value |
| --- | --- |
| Kotlin / Android namespace | `dev.kiyo9w.kmpboilerplate` |
| `applicationId` | `dev.kiyo9w.kmpboilerplate` |
| iOS `BUNDLE_ID` | `dev.kiyo9w.kmpboilerplate` |
| iOS `APP_NAME` | `KMP Boilerplate` |
| Android `app_name` | `KMP Boilerplate` |

Replace the package in Kotlin sources, `androidApp/build.gradle.kts` (`namespace` and `applicationId`), `shared/build.gradle.kts` (`namespace` and SQLDelight `packageName`), and the SQLDelight folder `shared/src/commonMain/sqldelight/dev/kiyo9w/kmpboilerplate/`.

Then set the display names:

- `iosApp/Configuration/Config.xcconfig` `BUNDLE_ID` and `APP_NAME`
- `androidApp/src/main/res/values/strings.xml` and `values-vi/strings.xml` `app_name`
- `iosApp/iosApp/Localizable.xcstrings` `app_name`

`PRODUCT_BUNDLE_IDENTIFIER` is `${BUNDLE_ID}${TEAM_ID}`. Leave `TEAM_ID` empty until you sign with a real team, or the bundle id gains a suffix.

Walk the rename once after you type it. A missed `package` line is the usual break.

## 3. Delete the sample

Keep `core/`, `platform/`, `di/` (the `coreModule` shape), `data/local` driver factories, `domain/session`, and `data/session`.

Remove:

- `domain/catalog/`
- `data/catalog/`
- `data/network/CatalogApi.kt`
- `feature/catalog/`
- `data/local/CatalogCache.kt` and `Catalog.sq` after you have your own tables
- `catalogModule()` from `di/Koin.kt`
- `androidApp/.../screens/Catalog*.kt`
- `iosApp/iosApp/ListView.swift` and `DetailView.swift`

Point `App.kt` and `iOSApp.swift` at the first real screen.

## 4. Add the first feature

Copy `catalogModule()`:

1. Model + repository interface in `domain/<name>/`.
2. API, cache, and impl in `data/`.
3. Bind a new `fun <name>Module()` next to `catalogModule()`.
4. ViewModel in `feature/<name>/` with `@NativeCoroutinesState` on every Flow SwiftUI reads.
5. Compose screen in `androidApp`.
6. SwiftUI view in `iosApp`.

Navigation stays in the apps. Shared records `SessionStore.setLastRoute(...)` when a screen opens.

## 5. Wire a phone API

Use official Android + official iOS, then one shared interface. `platform/AppVersion` is the worked example.

Prompt to run against Android and Apple docs, not against Dart:

```
I am building a Kotlin Multiplatform app. I need a feature that handles [feature].
Find the standard native Android library (like Jetpack) and the standard iOS native
framework (like AVFoundation). Now, write a unified KMP expect/actual bridge in
Kotlin that hooks into these native libraries for both platforms.
```

Rules that keep the bridge compilable:

- Shared code sees only the common interface or `expect` API.
- Android implementation lives in `androidMain`. iOS implementation lives in `iosMain`.
- Bind the native type from each app or from the matching source set. An Android-only Maven artifact stays out of `commonMain`.

`AppVersionReader` uses `PackageManager.getPackageInfo` on Android and `NSBundle.objectForInfoDictionaryKey` on iOS. Copy that folder when the next device job appears.

## 6. Run

Android, after `sdk.dir` is a real SDK:

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
./gradlew :androidApp:installDebug
```

iOS: open `iosApp/iosApp.xcodeproj`, run the `iosApp` scheme. First sync builds the `Shared` framework.

Tests that run without a phone:

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
./gradlew :shared:jvmTest :shared:compileTestKotlinIosSimulatorArm64
```

`:shared:allTests` also compiles and runs the common tests on each target. On this machine CoreSimulator has OOM'd (`Cannot allocate memory`). A compile of `compileTestKotlinIosSimulatorArm64` is the iOS gate. A simulator crash is a named miss, not a pass.

Flavor switch without BuildKonfig:

- Android: `./gradlew :androidApp:assembleDebug -Papp.environment=staging`
- iOS: set `APP_ENVIRONMENT=staging` in `iosApp/Configuration/Config.xcconfig`

Debug defaults to `debug`. Release defaults to `prod`. All three flavors share `https://dog.ceo/api` until a second URL exists.
