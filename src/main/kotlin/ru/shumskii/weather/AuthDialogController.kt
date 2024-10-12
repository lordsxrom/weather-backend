package ru.shumskii.weather

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.shumskii.weather.data.entity.User
import ru.shumskii.weather.data.repository.UserRepository
import ru.shumskii.weather.domain.models.AuthType
import ru.shumskii.weather.ui.*
import ru.shumskii.weather.utils.*

@RestController
@RequestMapping("/${AuthDialogController.PAGE}")
internal class AuthDialogController(
    private val userRepository: UserRepository
) {

    @GetMapping("/$SHOW")
    fun showAuthDialog(
        @RequestParam(name = TYPE) type: AuthType,
        @RequestParam(name = EMAIL) email: String,
        @RequestParam(name = PASSWORD) password: String,
    ): ResponseEntity<DivanPatch> {
        return ResponseEntity(
            divanPatch {
                when (type) {
                    AuthType.SIGN_IN -> {
                        val user = userRepository.findByEmail(email)
                        if (user != null) {
                            if (user.password == password) {
                                renderPatch(
                                    content = renderDialogContent(
                                        text = Strings.AUTH_DIALOG_SUCCESS_TEXT_WELLCOME(email),
                                        actions = listOf(
                                            setVariableAction(
                                                name = VARIABLE_USER_ID,
                                                value = user.id,
                                            ),
                                            navigationAction(
                                                page = MainScreenController.PAGE,
                                                queries = mapOf(
                                                    USER_ID to user.id
                                                )
                                            ),
                                        ),
                                    ),
                                )
                            } else {
                                renderPatch(
                                    content = renderDialogContent(
                                        text = Strings.AUTH_DIALOG_ERROR_TEXT_INCORRECT,
                                        actions = hideDialogAction(
                                            dialog = PAGE,
                                        ).asList(),
                                    ),
                                    dismissAction = hideDialogAction(
                                        dialog = PAGE,
                                    ),
                                )
                            }
                        } else {
                            renderPatch(
                                content = renderDialogContent(
                                    text = Strings.AUTH_DIALOG_ERROR_TEXT_NOT_FOUND,
                                    actions = hideDialogAction(
                                        dialog = PAGE,
                                    ).asList(),
                                ),
                                dismissAction = hideDialogAction(
                                    dialog = PAGE,
                                ),
                            )
                        }
                    }

                    AuthType.SIGN_UP -> {
                        val user = userRepository.findByEmail(email)
                        if (user != null) {
                            renderPatch(
                                content = renderDialogContent(
                                    text = Strings.AUTH_DIALOG_ERROR_TEXT_ALREADY_EXIST(email),
                                    actions = hideDialogAction(
                                        dialog = PAGE,
                                    ).asList(),
                                ),
                                dismissAction = hideDialogAction(
                                    dialog = PAGE,
                                ),
                            )
                        } else {
                            val newUser = userRepository.save(
                                User(
                                    email = email,
                                    password = password,
                                    cities = emptyList(),
                                    darkTheme = false
                                )
                            )
                            renderPatch(
                                content = renderDialogContent(
                                    text = Strings.AUTH_DIALOG_SUCCESS_TEXT_CREATED(email),
                                    actions = listOf(
                                        setVariableAction(
                                            name = VARIABLE_USER_ID,
                                            value = newUser.id,
                                        ),
                                        navigationAction(
                                            page = MainScreenController.PAGE,
                                            queries = mapOf(
                                                USER_ID to newUser.id
                                            )
                                        ),
                                    ),
                                ),
                            )
                        }
                    }
                }
            },
            HttpStatus.OK
        )
    }

    @GetMapping("/$HIDE")
    fun hideAuthDialog(): ResponseEntity<DivanPatch> {
        return ResponseEntity(
            divanPatch {
                renderPatch(
                    content = null,
                )
            },
            HttpStatus.OK
        )
    }

    private fun DivScope.renderPatch(
        content: Div?,
        dismissAction: Action? = null,
    ): Patch {
        return patch(
            changes = listOf(
                patchChange(
                    id = DIALOG_ID,
                    items = listOf(
                        renderDialog(
                            items = listOfNotNull(content),
                            dismissAction = dismissAction,
                        ),
                    ),
                )
            ),
        )
    }

    private fun DivScope.renderDialogContent(
        text: String,
        actions: List<Action>,
    ): Div {
        return column(
            width = matchParentSize(),
            height = wrapContentSize(),
            background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
            alignmentVertical = center,
            alignmentHorizontal = center,
            margins = edgeInsets(start = 32, end = 32),
            border = border(cornerRadius = 24, hasShadow = true),
            paddings = edgeInsets(all = 16),
            action = action(isEnabled = false),
            actionAnimation = animation(name = no_animation),
            items = listOf(
                text(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    text = text,
                    fontWeight = medium,
                    fontSize = 16,
                    textColor = color(Colors.ON_SURFACE),
                    alignmentHorizontal = center,
                ),
                renderButton(
                    text = Strings.AUTH_DIALOG_BUTTON_TEXT,
                    actions = actions,
                    margins = edgeInsets(top = 24),
                ),
            )
        )
    }

    companion object {
        const val PAGE = "auth-dialog"
    }

}