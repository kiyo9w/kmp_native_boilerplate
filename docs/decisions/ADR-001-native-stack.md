# ADR-001: Native-UI KMP stack

## Status

Accepted

## Date

2026-08-15

## Context

This repo is a reusable starter for products that want one shared Kotlin core and two native UIs. It is the KMP cousin of `zeref278/flutter_boilerplate`, not a Compose Multiplatform app and not a GitHub fork of the official template.

Agents repeatedly reach for "more meta" libraries (SKIE, shared navigation, shared Compose, extra codegen). Those choices are expensive to reverse and fight the two-agent native split.

## Decision

Keep this stack until a later ADR replaces a row:

| Job | Choice | Why |
| --- | --- | --- |
| UI | Jetpack Compose + SwiftUI | Each OS keeps its own pixels. Official native template shape |
| State | KMP-ObservableViewModel + StateFlow | Matches Bloc's job without a shared widget tree |
| Swift Flows | KMP-NativeCoroutines | What the official native template already wires |
| HTTP + JSON | Ktor 3 + kotlinx.serialization | Multiplatform, typed models, replaceable base URL |
| SQL | SQLDelight | Schema in `.sq`, JVM driver for tests without iOS sqlite natives |
| Settings | multiplatform-settings | Session, last route, flags |
| DI | Koin | Closest GetIt cousin, copyable modules |
| Log | Kermit | One logger in shared |
| Crash | `CrashReporter` no-op | Apps bind Sentry or Crashlytics later |
| Nav | Navigation Compose + NavigationStack | Shared owns no back stack |
| i18n | Native string catalogs | Chrome copy is UI. moko-resources waits for shared sentences |
| Flavor | `Flavor` three values | No BuildKonfig until a second URL exists |
| Device APIs | `platform/` interface + two official natives | `using-mobile-packages` rule |

Kotlin 2.4, AGP 9, `kotlin { android { } }` (not deprecated `androidLibrary`). Android CI runs on `ubuntu-latest` with `:shared:jvmTest`. iOS CI compiles shared tests and `xcodebuild` for arm64 simulator.

## Alternatives considered

### Compose Multiplatform shared UI

One codebase for pixels. Rejected: this kit exists so a Swift agent and a Compose agent can work in parallel. CMP is a different starter.

### SKIE

Richer Swift types. Rejected: official template and this kit already use NativeCoroutines. Pairing both is a known footgun.

### Room KMP

Google-owned SQL. Rejected for now: SQLDelight already has a schema, drivers, and a JVM test. Switch only if a product needs Room migrations the team already knows.

### Ktorfit / kotlin-inject / Voyager / Decompose / moko-resources

Each solves a real job. Each adds codegen or shared navigation or shared strings this kit does not need on day one. Trigger to revisit is written in `docs/OSS.md`.

## Consequences

- New features copy `catalogModule()` and `platform/AppVersion`.
- A stack change starts with `docs/decisions/ADR-NNN-*.md`.
- Agents load `AGENTS.md` and `docs/CODING.md` instead of re-litigating the table.
