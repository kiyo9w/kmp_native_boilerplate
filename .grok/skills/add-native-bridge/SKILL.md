---
name: add-native-bridge
description: >
  Add a phone API to this KMP kit via official Android + official iOS + one
  shared interface. Use for vibration, locale, files, version, camera, audio,
  Bluetooth, notifications, or any device job. Use when the user runs
  /add-native-bridge. Never port a Flutter plugin.
---

# Add a native bridge

This repo follows `using-mobile-packages`. Official Android API + official iOS framework first. `platform/AppVersion` is the worked example.

## Prompt (run against Android and Apple docs)

```
I am building a Kotlin Multiplatform app. I need a feature that handles [feature].
Find the standard native Android library (like Jetpack) and the standard iOS native
framework (like AVFoundation). Now, write a unified KMP expect/actual bridge in
Kotlin that hooks into these native libraries for both platforms.
```

## Shape

1. Shared interface or `expect` in `shared/src/commonMain/kotlin/dev/kiyo9w/kmpboilerplate/platform/`.
2. Android class in `androidMain` (Context-taking types belong here).
3. iOS class in `iosMain` (NSBundle, UIKit, AVFoundation, and friends).
4. Bind in `initKoin` / `initKoinIos`. Apps may pass the impl as they do `AppVersionReader`.

Prefer a thin interface plus two classes when Android needs `Context`. Prefer `expect`/`actual` when both sides are parameterless.

## Checks

- The Android type exists in current Jetpack or platform docs.
- The iOS type exists in current Apple docs.
- Shared code sees only the common type.
- An Android-only Maven artifact stays in `androidMain`.

## Verify

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
./gradlew :shared:compileKotlinIosSimulatorArm64 :shared:jvmTest
```
