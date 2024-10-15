package ru.shumskii.weather.presentation.ui_kit.base

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import ru.shumskii.weather.presentation.ui_kit.resources.Colors

fun DivScope.renderButton(
    text: String,
    actions: List<Action>,
    margins: EdgeInsets = edgeInsets(),
    textColor: Color = color(Colors.ON_TERTIARY),
    background: List<Background> = solidBackground(color(Colors.TERTIARY)).asList(),
): Div {
    return text(
        width = matchParentSize(),
        height = fixedSize(50),
        text = text,
        fontWeight = medium,
        fontSize = 16,
        textColor = textColor,
        margins = margins,
        actions = actions,
        background = background,
        border = border(cornerRadius = 8),
        textAlignmentHorizontal = center,
        textAlignmentVertical = center,
    )
}

fun DivScope.renderOutlinedButton(
    text: String,
    actions: List<Action>,
    margins: EdgeInsets = edgeInsets(),
): Div {
    return text(
        width = matchParentSize(),
        height = fixedSize(50),
        text = text,
        fontWeight = medium,
        fontSize = 16,
        textColor = color(Colors.TERTIARY),
        margins = margins,
        actions = actions,
        border = border(
            cornerRadius = 8,
            stroke = stroke(
                color = color(Colors.TERTIARY),
                width = 1.0
            )
        ),
        textAlignmentHorizontal = center,
        textAlignmentVertical = center,
    )
}

