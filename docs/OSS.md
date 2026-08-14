# OSS map

Search date: 2026-08-15. Starred-repo pass skipped on purpose. Public GitHub only.

This kit started from the official native template and then took the smallest package that matches each job in `zeref278/flutter_boilerplate`.

## Flutter row to KMP row

| Flutter boilerplate | Here | Mode |
| --- | --- | --- |
| Flutter widgets | SwiftUI + Compose | start-from official native template |
| Bloc | KMP-ObservableViewModel + StateFlow | depend |
| Retrofit + Dio | Ktor Client + kotlinx.serialization | depend |
| Floor | SQLDelight | depend |
| SharedPreferences | multiplatform-settings | depend |
| GetIt | Koin | depend |
| GoRouter | native NavigationStack / Navigation Compose | leave-native |
| Freezed | data classes + kotlinx.serialization | depend |
| intl | Android strings + iOS copy | leave-native for now |
| Logger | Kermit | depend |
| Crashlytics template | `CrashReporter` no-op | steal-pattern |
| CI | GitHub Actions Android + iOS | steal-pattern |

## Repos we used

| Problem | Repo | Stars | License | How |
| --- | --- | --- | --- | --- |
| Native-UI skeleton | [Kotlin/KMP-App-Template-Native](https://github.com/Kotlin/KMP-App-Template-Native) | 274 | Apache-2.0 | cloned, not forked, rewritten |
| HTTP | [ktorio/ktor](https://github.com/ktorio/ktor) | 14k+ | Apache-2.0 | depend |
| JSON | [Kotlin/kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) | 5.9k | Apache-2.0 | depend |
| SQL | [sqldelight/sqldelight](https://github.com/sqldelight/sqldelight) | 6.8k | Apache-2.0 | depend |
| Settings | [russhwolf/multiplatform-settings](https://github.com/russhwolf/multiplatform-settings) | 2.2k | Apache-2.0 | depend |
| DI | [InsertKoinIO/koin](https://github.com/InsertKoinIO/koin) | 10k | Apache-2.0 | depend |
| Log | [touchlab/Kermit](https://github.com/touchlab/Kermit) | (Touchlab) | Apache-2.0 | depend |
| Shared VM | [rickclephas/KMP-ObservableViewModel](https://github.com/rickclephas/KMP-ObservableViewModel) | 703 | MIT | depend |
| Swift Flows | [rickclephas/KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines) | 1324 | MIT | depend |
| Catalog of more | [terrakok/kmp-awesome](https://github.com/terrakok/kmp-awesome) | 5831 | — | browse |

## Looked at, not adopted as the start

| Repo | Why it stayed out |
| --- | --- |
| [Kotlin/KMP-App-Template](https://github.com/Kotlin/KMP-App-Template) | Shared Compose UI. Opposite of two-agent native shells |
| [touchlab/KaMPKit](https://github.com/touchlab/KaMPKit) | Stronger kit, but SKIE + its own sample. We stole practice, not the tree |
| [touchlab/SKIE](https://github.com/touchlab/SKIE) | Do not pair with NativeCoroutines. Official template already uses NativeCoroutines |
| [joreilly/PeopleInSpace](https://github.com/joreilly/PeopleInSpace) | Living sample with Wear/Desktop/Wasm. Too wide to start from |
| [dbaroncelli/D-KMP-sample](https://github.com/dbaroncelli/D-KMP-sample) | Shared navigation. Author froze it and points at Compose Multiplatform |
| [icerockdev/moko-resources](https://github.com/icerockdev/moko-resources) | Add when chrome i18n is a real product need |
| [evant/kotlin-inject](https://github.com/evant/kotlin-inject) | Compile-time DI. Koin already matches GetIt |

## Agent rule

Write product UI in `iosApp/` and `androidApp/` only. Write rules and IO in `shared/`. If a new library appears in `kmp-awesome`, add a row here before depending on it.
