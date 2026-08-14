# kmp_native_boilerplate

Native-UI Kotlin Multiplatform starter. Shared Kotlin owns rules, IO, and ViewModels. `androidApp` (Compose) and `iosApp` (SwiftUI) own pixels and navigation.

A new product starts by following `docs/ADOPT.md`. A new feature follows `docs/CODING.md`. Load `.grok/skills/add-kmp-feature` or `.grok/skills/add-native-bridge` for the matching job.

## Locks

- Shared module: domain, data, Koin, ViewModels, `platform/` bridges.
- Apps: theme, routing, widgets, string catalogs.
- Two native UIs. Compose Multiplatform is a different kit. Open an ADR before changing that.
- Swift reads Flows through KMP-NativeCoroutines. Keep SKIE out so this bridge stays the one SwiftUI path.
- Navigation lives in Navigation Compose and `NavigationStack`. Shared stores `SessionStore.lastRoute` only.
- Phone APIs enter `platform/` as a shared interface plus official Android and official iOS implementations. Android-only Maven artifacts stay in `androidMain`.
- Sample catalog is teaching code. Delete it with `docs/ADOPT.md` when the first real feature lands.
- Git author is `kiyo9w <ngokapikapi@gmail.com>`. Conventional commits, English. Docs and code in separate commits.

## Stack (keep unless an ADR says otherwise)

Ktor 3 + kotlinx.serialization, SQLDelight, multiplatform-settings, Koin, Kermit, KMP-ObservableViewModel, KMP-NativeCoroutines, Coil 3 on Android. Flavors are `Flavor.Debug` / `Staging` / `Prod` selected by each app. Crash reporting is `CrashReporter` passed into `initKoin`.

## Where new code goes

| Need | Location |
| --- | --- |
| Model + repository contract | `shared/.../domain/<feature>/` |
| HTTP, SQL, settings | `shared/.../data/` |
| Koin | `di/Koin.kt`, copy `catalogModule()` |
| Screen state | `shared/.../feature/<feature>/` |
| Device API | `shared/.../platform/` |
| Pixels | `androidApp/.../screens/` and `iosApp/iosApp/` |

ViewModels talk to domain interfaces and receive `HttpClient` users from Koin. Mark every Flow SwiftUI reads with `@NativeCoroutinesState`.

## Verify

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
./gradlew :shared:jvmTest :shared:compileTestKotlinIosSimulatorArm64
```

Android assemble needs a real `sdk.dir`. iOS command-line compile uses `xcodebuild` with `ARCHS=arm64 EXCLUDED_ARCHS=x86_64 CODE_SIGNING_ALLOWED=NO`. iOS links `-lsqlite3`.

A failing test is worse than a skipped simulator run. CoreSimulator OOM is a named miss, not a pass.

## Agent workflow

1. Read this file, then `docs/CODING.md` and the existing feature you will copy.
2. State assumptions before a non-trivial change.
3. Touch only the feature slice. Leave adjacent style alone.
4. Prove the slice with a test or the compile command above.
5. If a lock must move, write `docs/decisions/ADR-NNN-*.md` first.

Requests that need an ADR: shared UI, shared back stack, SKIE, BuildKonfig, a second API base URL, or an Android-only artifact in `commonMain`.
