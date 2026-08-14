---
name: design-tokens
description: >
  Keep this kit's UI on ThemeTokens and the platform color scheme.
  Use when writing or reviewing Compose or SwiftUI, picking spacing, radius,
  duration, or tap size, or when raw 8/12/16 numbers appear in screens.
---

# Design tokens

Feature UI reads `ThemeTokens` for spacing, radius, duration, and tap size. Colors come from Material3 or SwiftUI semantic colors.

## Catalog

`shared/.../core/ThemeTokens.kt` is the source. Android maps it in `androidApp/.../ui/ThemeTokens.android.kt`. iOS maps it in `iosApp/iosApp/KitTheme.swift`.

| Need | Token |
| --- | --- |
| Tight gap | `spaceXs` (4) |
| Related items | `spaceSm` (8) |
| Screen padding | `spaceMd` (16) |
| Section break | `spaceLg` (24) |
| Corner on cards | `radiusMd` (12) |
| Tappable control | `tapMin` (48) |

If the nearest token is close enough, use it. A new token belongs in `ThemeTokens` first, then both app mappings.

## Shared chrome

Reuse `KitEmpty` and `KitBanner` for empty and inline error. Feature-private widgets stay next to the screen. Chrome used twice lives in `androidApp/.../ui/` or `iosApp/iosApp/KitTheme.swift`.

## Screen states

Every list or detail that loads data shows loading, populated, empty, and error. When a refresh fails and cache exists, keep the list and show `KitBanner`. Empty includes a retry action.

## Check before handoff

Search the diff for leftover `8.dp`, `16.dp`, `padding(16)`, and hex colors in feature screens. Toggle light and dark once. Tap targets meet `tapMin`.
