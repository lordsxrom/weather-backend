package ru.shumskii.weather.ui.custom

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import ru.shumskii.weather.ui.resources.Colors
import ru.shumskii.weather.ui.resources.Images

fun DivScope.renderTitledInput(
    titleText: String,
    keyboardType: Input.KeyboardType,
    hintText: String,
    textVariable: String,
    margins: EdgeInsets = edgeInsets(),
): Div {
    return column(
        width = matchParentSize(),
        height = wrapContentSize(),
        margins = margins,
        items = listOf(
            text(
                width = wrapContentSize(),
                height = wrapContentSize(),
                text = titleText,
                fontWeight = regular,
                fontSize = 16,
                textColor = color(Colors.ON_SURFACE),
            ),
            stack(
                width = matchParentSize(),
                height = fixedSize(50),
                border = border(cornerRadius = 8),
                margins = edgeInsets(top = 10),
                paddings = edgeInsets(start = 20),
                background = solidBackground(color(Colors.SURFACE_CONTAINER_HIGHEST)).asList(),
                items = listOf(
                    input(
                        width = matchParentSize(),
                        height = wrapContentSize(),
                        textVariable = textVariable,
                        keyboardType = keyboardType,
                        hintText = hintText,
                        textColor = color(Colors.ON_SURFACE),
                        highlightColor = color(Colors.SECONDARY),
                        hintColor = color(Colors.ON_SURFACE),
                        fontSize = 14,
                        lineHeight = 22,
                        textAlignmentVertical = center,
                        alignmentVertical = center,
                    ),
                    container(
                        width = fixedSize(50),
                        height = fixedSize(50),
                        background = solidBackground(color(Colors.TERTIARY)).asList(),
                        border = border(cornerRadius = 8),
                        alignmentVertical = center,
                        alignmentHorizontal = end,
                        contentAlignmentVertical = center,
                        contentAlignmentHorizontal = center,
                        items = listOf(
                            image(
                                width = fixedSize(24),
                                height = fixedSize(24),
                                tintColor = color(Colors.ON_TERTIARY),
                                imageUrl = Url.create(
                                    when (keyboardType) {
                                        EmailEnumValue -> Images.EMAIL_ICON
                                        MultiLineTextEnumValue -> TODO()
                                        NumberEnumValue -> TODO()
                                        PasswordEnumValue -> Images.PASSWORD_ICON
                                        PhoneEnumValue -> TODO()
                                        SingleLineTextEnumValue -> Images.PENCIL_ICON
                                        UriEnumValue -> TODO()
                                    }
                                )
                            )
                        )
                    )
                ),
            ),
        )
    )
}