# OSS map

Search date: 2026-08-15. Starred-repo pass run with `list_starred.py` for KMP / Ktor / SQLDelight / Koin / Kermit / settings / Native UI. Authenticated GitHub user: `kiyo9w`.

The only starred hit that solves a named core is [Kotlin/KMP-App-Template-Native](https://github.com/Kotlin/KMP-App-Template-Native). The rest of the stack was already in this tree from the public KMP catalog. Public `gh search` on the leftover cores returned no better starter to replace the rewrite.

This kit started from that official native template and then took the smallest package that matches each job in `zeref278/flutter_boilerplate`.

## Flutter row to KMP row

| Flutter boilerplate | Here | Mode | In code |
| --- | --- | --- | --- |
| Flutter widgets | SwiftUI + Jetpack Compose | start-from official native template | `iosApp/`, `androidApp/` |
| Bloc + examples | KMP-ObservableViewModel + StateFlow, list + detail + loading / error / empty | depend | `feature/catalog/` |
| Retrofit + Dio + codegen models | Ktor Client 3 + kotlinx.serialization, typed `DogCeoResponse`, replaceable `Flavor.catalogBaseUrl` | depend | `data/network/` |
| Floor + examples | SQLDelight schema `Catalog.sq`, JVM sqlite test | depend | `data/local/`, `jvmTest` |
| SharedPreferences | multiplatform-settings for launch count, last opened, last route | depend | `data/session/` |
| GetIt | Koin `coreModule` + copyable `catalogModule` | depend | `di/Koin.kt` |
| GoRouter | Navigation Compose + SwiftUI `NavigationStack`. Shared owns no back stack | leave-native | `App.kt`, `ListView.swift` |
| Freezed | data classes + kotlinx.serialization | depend | `domain/`, `data/network/` |
| intl | Android `values` / `values-vi` + iOS `Localizable.xcstrings` | leave-native | app string catalogs |
| Logger | Kermit through `AppLog` | depend | `core/AppLog.kt` |
| Crashlytics template | `CrashReporter` passed into `initKoin`, no-op default | steal-pattern | `core/CrashReporter.kt` |
| DarkTheme | system light/dark on both apps | leave-native | DayNight + SwiftUI |
| Multi languages | native strings, English source, Vietnamese second locale | leave-native | `values-vi`, xcstrings |
| Unit tests | commonTest domain + repository + session. jvmTest real SQLDelight schema. iOS Kotlin tests compile | depend | `commonTest/`, `jvmTest/` |
| Integration test | CI assemble / compile is the substitute while this machine's simulator OOMs | steal-pattern | `.github/workflows/` |
| Flutter CI | GitHub Actions Android + iOS | steal-pattern | `build-android.yml`, `build-ios.yml` |
| Flavors | `Flavor.Debug` / `Staging` / `Prod` selected by the apps | leave-local | `core/AppConfig.kt` |
| Native device API (kit extra) | `AppVersionReader` via official PackageManager + Bundle | leave-native | `platform/` |

## Repos we used

| Problem | Repo | Stars | License | Source | How |
| --- | --- | --- | --- | --- | --- |
| Native-UI skeleton | [Kotlin/KMP-App-Template-Native](https://github.com/Kotlin/KMP-App-Template-Native) | 275 | Apache-2.0 | starred | cloned, not forked, rewritten |
| HTTP | [ktorio/ktor](https://github.com/ktorio/ktor) | 14k+ | Apache-2.0 | already in tree | depend |
| JSON | [Kotlin/kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) | 5.9k | Apache-2.0 | already in tree | depend |
| SQL | [sqldelight/sqldelight](https://github.com/sqldelight/sqldelight) | 6.8k | Apache-2.0 | already in tree | depend |
| Settings | [russhwolf/multiplatform-settings](https://github.com/russhwolf/multiplatform-settings) | 2.2k | Apache-2.0 | already in tree | depend |
| DI | [InsertKoinIO/koin](https://github.com/InsertKoinIO/koin) | 10k | Apache-2.0 | already in tree | depend |
| Log | [touchlab/Kermit](https://github.com/touchlab/Kermit) | Touchlab | Apache-2.0 | already in tree | depend |
| Shared VM | [rickclephas/KMP-ObservableViewModel](https://github.com/rickclephas/KMP-ObservableViewModel) | 703 | MIT | already in tree | depend |
| Swift Flows | [rickclephas/KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines) | 1324 | MIT | already in tree | depend |
| Catalog of more | [terrakok/kmp-awesome](https://github.com/terrakok/kmp-awesome) | 5831 | — | browse | browse |

## Looked at, left as later path

| Repo | Why the kit stayed on the current choice |
| --- | --- |
| [Kotlin/KMP-App-Template](https://github.com/Kotlin/KMP-App-Template) | Shared Compose UI. Opposite of two-agent native shells |
| [touchlab/KaMPKit](https://github.com/touchlab/KaMPKit) | Stronger kit, SKIE + its own sample. Practice only |
| [touchlab/SKIE](https://github.com/touchlab/SKIE) | Pairs poorly with NativeCoroutines. Official template already uses NativeCoroutines |
| [joreilly/PeopleInSpace](https://github.com/joreilly/PeopleInSpace) | Living sample with Wear / Desktop / Wasm. Too wide to start from |
| [dbaroncelli/D-KMP-sample](https://github.com/dbaroncelli/D-KMP-sample) | Shared navigation. Author froze it and points at Compose Multiplatform |
| [icerockdev/moko-resources](https://github.com/icerockdev/moko-resources) | Add when chrome i18n must live in shared Kotlin |
| [evant/kotlin-inject](https://github.com/evant/kotlin-inject) | Compile-time DI. Koin already matches GetIt |

## Agent rule

Write product UI in `iosApp/` and `androidApp/` only. Write rules and IO in `shared/`. If a new library appears in `kmp-awesome`, add a row here before depending on it.
