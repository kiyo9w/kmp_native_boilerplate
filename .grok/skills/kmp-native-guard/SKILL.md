---
name: kmp-native-guard
description: >
  Guard this native-UI KMP kit against architecture drift while implementing
  or reviewing a feature. Use when adding code, reviewing a diff, or when an
  agent is about to add a library. Use when the user runs /kmp-native-guard.
---

# KMP native guard

Load `AGENTS.md` and `docs/decisions/ADR-001-native-stack.md`. If the change fights a lock, stop and write an ADR instead of "upgrading" the kit in place.

## Allowed without an ADR

- New feature via `catalogModule()` copy (`docs/CODING.md`)
- New `platform/` bridge via official Android + official iOS
- Tests in `commonTest` / `jvmTest`
- Native strings in both apps
- Binding a real `CrashReporter` from an app module

## Needs an ADR first

Shared Compose UI, shared back stack, SKIE, BuildKonfig, a second API host, Room instead of SQLDelight, a second HTTP or DI stack, moko-resources, or an Android-only artifact in `commonMain`.

## Review questions

1. Does shared code still stop at ViewModels and IO?
2. Does each OS still own navigation and pixels?
3. Is every Swift-read Flow annotated `@NativeCoroutinesState`?
4. Did a new library get a row in `docs/OSS.md`?
5. Did `./gradlew :shared:jvmTest :shared:compileTestKotlinIosSimulatorArm64` run?

A failing test is worse than a skipped simulator run.
