package ru.shumskii.weather.ui.base

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import ru.shumskii.weather.ui.resources.Colors
import ru.shumskii.weather.ui.resources.Images

fun DivScope.renderAppBar(
    title: String,
    backButton: Div? = null,
): Div {
    return stack(
        width = matchParentSize(),
        height = fixedSize(56),
        background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
        items = listOfNotNull(
            backButton,
            text(
                width = wrapContentSize(),
                height = wrapContentSize(),
                text = title,
                fontSize = 18,
                alignmentVertical = center,
                alignmentHorizontal = center,
                textColor = color(Colors.ON_SURFACE)
            ),
        ),
    )
}

fun DivScope.renderBackButton(
    action: Action,
): Div {
    return image(
        width = fixedSize(24),
        height = fixedSize(24),
        imageUrl = Url.create(Images.APP_BAR_BACK_BUTTON_ICON_ARROW),
        alignmentVertical = center,
        alignmentHorizontal = start,
        action = action,
        margins = edgeInsets(start = 16)
    )
}