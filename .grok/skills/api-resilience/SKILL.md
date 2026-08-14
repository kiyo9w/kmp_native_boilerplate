---
name: api-resilience
description: >
  Keep shared network models and screens running when a payload is messy.
  Use when writing Ktor DTOs, repositories, or list/detail loading, or when
  a parse crash or blank screen appears after one failed request.
---

# API resilience

Assume the contract will wobble. Fail at the smallest scope.

## Models

`Json { ignoreUnknownKeys = true }` is already on the shared client. New `@Serializable` types give defaults for fields the host may omit (`""`, `0`, `emptyList()`). Only the identifier stays required.

`AppResult.Err` carries a short user message. Screens show that message, not a stack trace.

## Loading

Load independent pieces independently. A 404 on one section leaves the rest of the screen up.

List to detail: the list already wrote SQLDelight. Detail reads `observeItem(id)` from cache, then refresh in the background when needed.

Refresh error with cache: keep the list, show `KitBanner`, offer retry.

## Check before handoff

- Missing optional field still parses
- Empty list shows empty + retry
- One failed parallel call leaves the other data on screen
- Offline path shows a human sentence
