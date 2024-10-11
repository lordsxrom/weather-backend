package ru.shumskii.weather.ui

import divkit.dsl.*
import divkit.dsl.scope.DivScope

fun DivScope.renderButton(
    text: String,
    action: Action,
    margins: EdgeInsets = edgeInsets(),
): Div {
    return text(
        width = matchParentSize(),
        height = fixedSize(50),
        text = text,
        fontWeight = medium,
        fontSize = 16,
        textColor = color(Colors.ON_TERTIARY),
        margins = margins,
        action = action,
        background = solidBackground(color(Colors.TERTIARY)).asList(),
        border = border(cornerRadius = 8),
        textAlignmentHorizontal = center,
        textAlignmentVertical = center,
    )
}

fun DivScope.renderOutlinedButton(
    text: String,
    action: Action,
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
        action = action,
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

