package ru.shumskii.weather.ui

import divkit.dsl.*
import divkit.dsl.scope.DivScope

fun DivScope.renderBottomNavigationBar(
    stateId: Int,
): Div {
    return row(
        width = matchParentSize(),
        height = fixedSize(56),
        background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
        contentAlignmentVertical = center,
        items = listOf(
            renderNavigationItem(
                imageUrl = Images.NAVIGATION_ITEM_ICON_MAIN,
                text = Strings.NAVIGATION_ITEM_TEXT_MAIN,
                isSelected = stateId == 0,
                action = action(), // Do nothing
            ),
            renderNavigationItem(
                imageUrl = Images.NAVIGATION_ITEM_ICON_SETTINGS,
                text = Strings.NAVIGATION_ITEM_TEXT_SETTINGS,
                isSelected = stateId == 1,
                action = action(), // Do nothing
            ),
        ),
    )
}

private fun DivScope.renderNavigationItem(
    imageUrl: String,
    text: String,
    isSelected: Boolean,
    action: Action,
): Div {
    return column(
        width = matchParentSize(weight = 1.0),
        height = wrapContentSize(),
        contentAlignmentHorizontal = center,
        contentAlignmentVertical = center,
        action = action,
        paddings = edgeInsets(all = 4),
        items = listOf(
            image(
                height = fixedSize(24),
                width = fixedSize(24),
                imageUrl = Url.create(imageUrl),
                tintColor = if (isSelected) color(Colors.SURFACE_TINT) else color(Colors.ON_SURFACE),
            ),
            text(
                height = wrapContentSize(),
                width = wrapContentSize(),
                text = text,
                fontSize = 12,
                margins = edgeInsets(top = 2),
                textColor = if (isSelected) color(Colors.SURFACE_TINT) else color(Colors.ON_SURFACE),
            ),
        ),
    )
}