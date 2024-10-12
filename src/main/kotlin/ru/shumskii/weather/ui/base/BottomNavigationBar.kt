package ru.shumskii.weather.ui.base

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import ru.shumskii.weather.ui.resources.Colors
import ru.shumskii.weather.ui.resources.Images
import ru.shumskii.weather.ui.resources.Strings

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
        height = matchParentSize(),
        contentAlignmentHorizontal = center,
        contentAlignmentVertical = center,
        action = action,
        paddings = edgeInsets(all = 5),
        items = listOf(
            image(
                height = fixedSize(28),
                width = fixedSize(28),
                imageUrl = Url.create(imageUrl),
                tintColor = if (isSelected) color(Colors.SURFACE_TINT) else color(Colors.ON_SURFACE),
            ),
            text(
                height = wrapContentSize(),
                width = wrapContentSize(),
                text = text,
                fontSize = 10,
                margins = edgeInsets(top = 2),
                textColor = if (isSelected) color(Colors.SURFACE_TINT) else color(Colors.ON_SURFACE),
                fontWeight = medium,
            ),
        ),
    )
}