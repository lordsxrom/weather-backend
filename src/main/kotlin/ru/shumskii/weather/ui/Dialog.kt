package ru.shumskii.weather.ui

import divkit.dsl.*
import divkit.dsl.scope.DivScope

fun DivScope.renderDialog(
    items: List<Div> = emptyList(),
    dismissAction: Action? = null,
): Div {
    val show = items.isNotEmpty()
    return stack(
        id = DIALOG_ID,
        width = matchParentSize(),
        height = matchParentSize(),
        items = items,
        visibility = if (show) {
            visible
        } else {
            gone
        },
        action = if (show) {
            dismissAction
        } else {
            null
        },
    )
}

const val DIALOG_ID = "dialog"