package ru.shumskii.weather.presentation.ui_kit.base

import divkit.dsl.*
import divkit.dsl.scope.DivScope

fun DivScope.renderBottomSheet(
    items: List<Div> = emptyList(),
    dismissAction: Action? = null,
): Div {
    val show = items.isNotEmpty()
    return stack(
        id = BOTTOM_SHEET_ID,
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

const val BOTTOM_SHEET_ID = "bottom_sheet"