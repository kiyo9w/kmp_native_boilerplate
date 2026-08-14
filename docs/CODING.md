# Coding manual

For a human or agent adding a feature to this kit. Read `AGENTS.md` first. Copy an existing slice. Invent a second architecture only after an ADR.

## Before you type

1. Name the feature in one sentence.
2. Find the closest existing slice (today: catalog).
3. State assumptions. If two interpretations exist, stop and ask.
4. Touch only that slice. Leave adjacent formatting and comments alone.

## Feature recipe

Copy `catalogModule()`. The six steps stay in this order:

1. `shared/src/commonMain/kotlin/dev/kiyo9w/kmpboilerplate/domain/<feature>/`
   Model as a `data class`. Repository as an interface returning `AppResult` and `Flow`.
2. `shared/src/commonMain/kotlin/dev/kiyo9w/kmpboilerplate/data/`
   Ktor API types with `@Serializable`. SQLDelight `.sq` if you cache. Settings keys if the value is a flag, session, or last route.
3. `fun <feature>Module()` next to `catalogModule()` in `di/Koin.kt`. Load it from `initKoin`.
4. `shared/src/commonMain/kotlin/dev/kiyo9w/kmpboilerplate/feature/<feature>/`
   `ViewModel()` from KMP-ObservableViewModel. `@NativeCoroutinesState` on every `StateFlow` SwiftUI reads. Loading, error, and empty are first-class, not afterthoughts.
5. Compose screen in `androidApp/.../screens/`. Strings in `values/strings.xml` and `values-vi/strings.xml`. Route in `App.kt`.
6. SwiftUI view in `iosApp/iosApp/`. Strings in `Localizable.xcstrings`. Route in `NavigationStack`.

Shared records `SessionStore.setLastRoute(...)`. Apps own the back stack.

## Device API recipe

Use `.grok/skills/add-native-bridge`. Short form:

- Official Android API + official iOS framework.
- Shared interface in `platform/`.
- Android class in `androidMain`, iOS class in `iosMain`.
- Bind from `initKoin` / `initKoinIos`.
- `AppVersionReader` is the worked example.

## Tests

| Kind | Where | What it proves |
| --- | --- | --- |
| Domain / mapping | `commonTest` | Pure rules |
| Repository | `commonTest` with fakes | Cache and error policy |
| SQLDelight schema | `jvmTest` + `JdbcSqliteDriver` | Real `.sq` file |
| iOS compile | `./gradlew :shared:compileTestKotlinIosSimulatorArm64` | Shared tests compile for iOS |

`commonTest` stays free of SQLite natives. A new repository test uses `InMemoryCatalogCache` or a new in-memory fake.

## UI rules

- Chrome copy lives in native catalogs. English source, Vietnamese second locale.
- Both apps follow the system light/dark setting (`Theme.KmpBoilerplate`, SwiftUI default).
- Hyphen in UI strings, not an em dash.
- Apps call `stringResource` / `String(localized:)`. Shared Kotlin stays language-agnostic.

## Flavor and crash hook

- Change environment with `-Papp.environment=staging` or iOS `APP_ENVIRONMENT`.
- Bind a real `CrashReporter` from each app when Sentry or Crashlytics is added. Keep the SDK out of `shared`.

## Verify before you commit

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
./gradlew :shared:jvmTest :shared:compileTestKotlinIosSimulatorArm64
```

Android assemble needs a real `sdk.dir`. iOS `xcodebuild` needs `ARCHS=arm64 EXCLUDED_ARCHS=x86_64` and `-lsqlite3` (already in the project).

Conventional commits, English. Docs and code in separate commits. Author `kiyo9w <ngokapikapi@gmail.com>`.

## Chaos guards

These are the usual ways a fresh agent wrecks the kit:

| Drift | What to do instead |
| --- | --- |
| New shared UI toolkit | Keep Compose in `androidApp` and SwiftUI in `iosApp` |
| Shared navigation library | Keep native nav. Store a route string only |
| SKIE next to NativeCoroutines | Stay on NativeCoroutines |
| Android-only library in `commonMain` | Put it in `androidMain` or write a `platform/` bridge |
| Second HTTP client or DI container | Extend Ktor and Koin |
| Hardcoded English in Swift/Compose | Add a catalog key |
| Silent `try/catch` | Log with `AppLog` and return `AppResult.Err` |
| Drive-by rename of the sample | Delete the sample only when adopting a product (`docs/ADOPT.md`) |
