# Agent contract

This repository is a reusable Kotlin Multiplatform starter. Shared Kotlin owns rules, IO, and screen state. `androidApp` and `iosApp` own pixels and navigation.

## Product locks

- Shared module owns domain, data, Koin modules, and ViewModels. Apps own Compose and SwiftUI.
- Two native UIs stay native. Compose Multiplatform is a different kit, started only when a product owner asks for one shared UI.
- Swift reads Flows through KMP-NativeCoroutines. SKIE stays out of this tree so the official native template bridge keeps working.
- Git author is `kiyo9w <ngokapikapi@gmail.com>`. Conventional commits, English.
- Sample catalog is teaching code. A new product deletes it in the four steps in `docs/ADOPT.md`.
- Phone APIs enter through `platform/` as a shared interface plus two official native implementations. Android-only Maven artifacts stay out of `commonMain`.
- Navigation lives in Navigation Compose and `NavigationStack`. Shared code records a last-route string. It does not own a back stack.

## Done looks like

A cold agent can clone this repo, rename the package, delete the sample, add one feature, and run both apps without rewriting layers.

## Where new work goes

| Need | Location |
| --- | --- |
| Model and repository contract | `shared/.../domain/<feature>/` |
| HTTP, SQL, settings | `shared/.../data/` |
| Koin bindings | `shared/.../di/Koin.kt`, copy `catalogModule()` |
| Screen state | `shared/.../feature/<feature>/` |
| Device API | `shared/.../platform/` |
| Pixels | `androidApp/.../screens/` and `iosApp/iosApp/` |

Read `docs/ARCHITECTURE.md` for layers and `docs/ADOPT.md` for the first-product recipe.
