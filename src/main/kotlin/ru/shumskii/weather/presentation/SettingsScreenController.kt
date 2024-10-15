package ru.shumskii.weather.presentation

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.shumskii.weather.data.repository.UserRepository
import ru.shumskii.weather.data.repository.WeatherRepository
import ru.shumskii.weather.presentation.ui_kit.base.ROOT_SCAFFOLD_ID
import ru.shumskii.weather.presentation.ui_kit.base.renderAppBar
import ru.shumskii.weather.presentation.ui_kit.base.renderBottomNavigationBar
import ru.shumskii.weather.presentation.ui_kit.base.renderScaffold
import ru.shumskii.weather.presentation.ui_kit.resources.Colors
import ru.shumskii.weather.presentation.ui_kit.resources.Strings
import ru.shumskii.weather.utils.CITY
import ru.shumskii.weather.utils.USER_ID

@RestController
@RequestMapping("/${SettingsScreenController.PAGE}")
class SettingsScreenController(
    private val userRepository: UserRepository,
) {

    @GetMapping
    fun getSettingsScreen(
        @RequestParam(name = USER_ID) userId: String,
    ): ResponseEntity<DivanPatch> {
        return ResponseEntity(
            divanPatch {
                patch(
                    changes = listOf(
                        patchChange(
                            id = ROOT_SCAFFOLD_ID,
                            items = listOf(
                                renderScaffold(
                                    appBar = renderAppBar(
                                        title = "Settings",
                                    ),
                                    body = renderBody(
                                    ),
                                    bottomNavigationBar = renderBottomNavigationBar(
                                        stateId = 1,
                                    ),
                                    backgroundColor = solidBackground(color(Colors.SURFACE)).asList(),
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
        return text(
            width = wrapContentSize(),
            height = wrapContentSize(),
            text = "TDB",
            alignmentVertical = center,
            alignmentHorizontal = center,
        )
    }

    companion object {
        const val PAGE = "settings"
    }
}