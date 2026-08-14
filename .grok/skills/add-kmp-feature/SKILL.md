---
name: add-kmp-feature
description: >
  Add a product feature to this native-UI KMP kit by copying catalogModule.
  Use when the user asks to add a feature, screen, list, detail, repository,
  ViewModel, or API slice. Use when they run /add-kmp-feature.
---

# Add a KMP feature

Read `AGENTS.md` and `docs/CODING.md` first. Copy the catalog slice. Do not invent a second layering.

## Steps

1. Domain model + repository interface in `shared/src/commonMain/kotlin/dev/kiyo9w/kmpboilerplate/domain/<feature>/`.
2. Ktor types, cache, and impl in `shared/.../data/`. Settings only for session, last route, or a product flag.
3. `fun <feature>Module()` in `di/Koin.kt`. Load it from `initKoin`.
4. ViewModel in `feature/<feature>/` with `@NativeCoroutinesState` on every Flow SwiftUI reads. Loading, error, empty.
5. Compose screen + `values` / `values-vi` strings + route in `App.kt`.
6. SwiftUI view + `Localizable.xcstrings` + `NavigationStack` link.
7. `commonTest` for domain and repository. `jvmTest` if you touch a `.sq` file.

Shared records `SessionStore.setLastRoute`. Apps own the back stack. ViewModels do not construct `HttpClient`.

## Verify

```
export GRADLE_USER_HOME="$PWD/.gradle-home"
./gradlew :shared:jvmTest :shared:compileTestKotlinIosSimulatorArm64
```

Commit code, then docs, as `kiyo9w <ngokapikapi@gmail.com>`.
