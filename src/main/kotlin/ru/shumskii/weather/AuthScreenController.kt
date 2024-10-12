package ru.shumskii.weather

import divkit.dsl.*
import divkit.dsl.core.expression
import divkit.dsl.scope.DivScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.shumskii.weather.domain.models.AuthType
import ru.shumskii.weather.ui.*
import ru.shumskii.weather.utils.EMAIL
import ru.shumskii.weather.utils.PASSWORD
import ru.shumskii.weather.utils.TYPE

@RestController
@RequestMapping("/${AuthScreenController.PAGE}")
class AuthScreenController {

    @GetMapping
    fun getAuthScreen(
        @RequestHeader headers: Map<String, String>,
    ): ResponseEntity<DivanPatch> {
        return ResponseEntity(
            divanPatch {
                patch(
                    changes = listOf(
                        patchChange(
                            id = ROOT_SCAFFOLD_ID,
                            items = listOf(
                                renderScaffold(
                                    body = renderBody(),
                                    backgroundColor = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
                                )
                            )
                        )
                    )
                )
            },
            HttpStatus.OK
        )
    }

    private fun DivScope.renderBody(): Div {
        return column(
            width = matchParentSize(),
            height = wrapContentSize(),
            alignmentVertical = center,
            alignmentHorizontal = center,
            margins = edgeInsets(start = 24, end = 24),
            items = listOf(
                image(
                    width = fixedSize(128),
                    height = fixedSize(128),
                    imageUrl = Url.create(Images.APPLICATION_IMAGE),
                    alignmentHorizontal = center,
                ),
                text(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    text = Strings.AUTH_TITLE,
                    fontWeight = medium,
                    fontSize = 16,
                    textColor = color(Colors.ON_SURFACE),
                    alignmentHorizontal = center,
                    margins = edgeInsets(top = 16),
                ),
                renderTitledInput(
                    titleText = Strings.AUTH_INPUT_TITLE_EMAIL,
                    keyboardType = email,
                    hintText = Strings.AUTH_INPUT_HINT_EMAIL,
                    textVariable = PRIVATE_VARIABLE_EMAIL,
                    margins = edgeInsets(top = 44),
                ),
                renderTitledInput(
                    titleText = Strings.AUTH_INPUT_TITLE_PASSWORD,
                    keyboardType = password,
                    hintText = Strings.AUTH_INPUT_HINT_PASSWORD,
                    textVariable = PRIVATE_VARIABLE_PASSWORD,
                    margins = edgeInsets(top = 28),
                ),
                renderButton(
                    text = Strings.AUTH_BUTTON_TEXT_SIGN_IN,
                    action = showDialogAction(
                        dialog = AuthDialogController.PAGE,
                        queries = mapOf(
                            TYPE to AuthType.SIGN_IN,
                            EMAIL to "@{$PRIVATE_VARIABLE_EMAIL}",
                            PASSWORD to "@{$PRIVATE_VARIABLE_PASSWORD}",
                        ),
                        isEnabledExpression = expression("@{len($PRIVATE_VARIABLE_EMAIL) > 0 && len($PRIVATE_VARIABLE_PASSWORD) > 0}")
                    ),
                    margins = edgeInsets(top = 28),
                ),
                renderAuthDivider(
                    margins = edgeInsets(top = 40),
                ),
                renderOutlinedButton(
                    text = Strings.AUTH_BUTTON_TEXT_SIGN_UP,
                    action = showDialogAction(
                        dialog = AuthDialogController.PAGE,
                        queries = mapOf(
                            TYPE to AuthType.SIGN_UP,
                            EMAIL to "@{$PRIVATE_VARIABLE_EMAIL}",
                            PASSWORD to "@{$PRIVATE_VARIABLE_PASSWORD}",
                        ),
                        isEnabledExpression = expression("@{len($PRIVATE_VARIABLE_EMAIL) > 0 && len($PRIVATE_VARIABLE_PASSWORD) > 0}")
                    ),
                    margins = edgeInsets(top = 40),
                )
            )
        )
    }

    private fun DivScope.renderAuthDivider(
        margins: EdgeInsets = edgeInsets(),
    ): Div {
        return row(
            width = matchParentSize(),
            height = wrapContentSize(),
            items = listOf(
                container(
                    width = matchParentSize(),
                    height = fixedSize(1),
                    background = solidBackground(color(Colors.ON_SURFACE_VARIANT)).asList(),
                ),
                text(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    text = Strings.AUTH_DIVIDER_TEXT,
                    textColor = color(Colors.ON_SURFACE_VARIANT),
                    fontSize = 14,
                    margins = edgeInsets(start = 20, end = 20)
                ),
                container(
                    width = matchParentSize(),
                    height = fixedSize(1),
                    background = solidBackground(color(Colors.ON_SURFACE_VARIANT)).asList(),
                ),
            ),
            contentAlignmentVertical = center,
            margins = margins,
        )
    }

    private fun DivScope.renderTitledInput(
        titleText: String,
        keyboardType: Input.KeyboardType,
        hintText: String,
        textVariable: String,
        margins: EdgeInsets,
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
                                            SingleLineTextEnumValue -> TODO()
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

    companion object {
        const val PAGE = "auth"
    }

}