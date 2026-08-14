package dev.kiyo9w.kmpboilerplate.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kiyo9w.kmpboilerplate.ui.KitEmpty

@Composable
fun EmptyScreenContent(
    message: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    KitEmpty(
        message = message,
        modifier = modifier,
        actionLabel = actionLabel,
        onAction = onAction,
    )
}
