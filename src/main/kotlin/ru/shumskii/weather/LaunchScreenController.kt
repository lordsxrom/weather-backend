package ru.shumskii.weather

import divkit.dsl.*
import divkit.dsl.core.expression
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.shumskii.weather.ui.Colors
import ru.shumskii.weather.ui.ROOT_SCAFFOLD_ID
import ru.shumskii.weather.ui.navigationAction

@RestController
@RequestMapping("/${LaunchScreenController.PAGE}")
class LaunchScreenController {

    @GetMapping
    fun getLaunchScreen(
        @RequestHeader headers: Map<String, String>,
        @RequestParam(name = VARIABLE_USER_ID) userId: String?,
    ): ResponseEntity<Divan> {
        return ResponseEntity(
            divan {
                data(
                    logId = PAGE,
                    div = stack(
                        id = ROOT_SCAFFOLD_ID,
                        width = matchParentSize(),
                        height = matchParentSize(),
                        background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
                    ),
                    variables = listOf(
                        stringVariable(
                            name = VARIABLE_HOST,
                            value = headers["host"]!!
                        ),
                        stringVariable(
                            name = VARIABLE_USER_ID,
                            value = userId.orEmpty(),
                        ),
                        stringVariable(
                            name = PRIVATE_VARIABLE_EMAIL,
                            value = "",
                        ),
                        stringVariable(
                            name = PRIVATE_VARIABLE_PASSWORD,
                            value = "",
                        ),
                    ),
                    variableTriggers = listOf(
                        trigger(
                            actions = listOf(
                                navigationAction(
                                    page = if (userId.isNullOrBlank()) {
                                        AuthScreenController.PAGE
                                    } else {
                                        "main"
                                    },
                                ),
                            ),
                            mode = on_variable,
                        ).evaluate(condition = expression("@{len($VARIABLE_HOST) > 0}")),
                    )
                )
            },
            HttpStatus.OK
        )
    }

    companion object {
        const val PAGE = "launch"
    }
}

const val PRIVATE_VARIABLE_EMAIL = "email_text_value"
const val PRIVATE_VARIABLE_PASSWORD = "password_text_value"

const val VARIABLE_USER_ID = "user_id"
const val VARIABLE_HOST = "host"