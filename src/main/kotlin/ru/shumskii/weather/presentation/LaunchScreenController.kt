package ru.shumskii.weather.presentation

import divkit.dsl.*
import divkit.dsl.core.expression
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.shumskii.weather.presentation.ui_kit.base.ROOT_SCAFFOLD_ID
import ru.shumskii.weather.presentation.ui_kit.base.navigationAction
import ru.shumskii.weather.presentation.ui_kit.resources.Colors
import ru.shumskii.weather.utils.HOST
import ru.shumskii.weather.utils.USER_ID

@RestController
@RequestMapping("/${LaunchScreenController.PAGE}")
class LaunchScreenController {

    @GetMapping
    fun getLaunchScreen(
        @RequestHeader headers: Map<String, String>,
        @RequestParam(name = USER_ID) userId: String?,
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
                            value = headers[HOST]!!
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
                        stringVariable(
                            name = PRIVATE_VARIABLE_CITY,
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
                                        TODO("use main page")
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

const val PRIVATE_VARIABLE_EMAIL = "private_variable_email"
const val PRIVATE_VARIABLE_PASSWORD = "private_variable_password"
const val PRIVATE_VARIABLE_CITY = "private_variable_city"

const val VARIABLE_HOST = "variable_host"
const val VARIABLE_USER_ID = "variable_user_id"