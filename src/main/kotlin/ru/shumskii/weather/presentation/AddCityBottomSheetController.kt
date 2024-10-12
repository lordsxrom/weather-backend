package ru.shumskii.weather.presentation

import divkit.dsl.*
import divkit.dsl.core.expression
import divkit.dsl.scope.DivScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.shumskii.weather.data.repository.UserRepository
import ru.shumskii.weather.presentation.ui_kit.base.*
import ru.shumskii.weather.presentation.ui_kit.custom.renderTitledInput
import ru.shumskii.weather.presentation.ui_kit.resources.Colors
import ru.shumskii.weather.presentation.ui_kit.resources.Strings
import ru.shumskii.weather.utils.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/${AddCityBottomSheetController.PAGE}")
internal class AddCityBottomSheetController(
    private val userRepository: UserRepository
) {

    @GetMapping("/$SHOW")
    fun showAddCityBottomSheet(): ResponseEntity<DivanPatch> {
        return ResponseEntity(
            divanPatch {
                patch(
                    changes = listOf(
                        patchChange(
                            id = BOTTOM_SHEET_ID,
                            items = listOf(
                                renderBottomSheet(
                                    items = listOf(
                                        renderBottomSheetContent(
                                            actions = listOf(
                                                updateDialogAction(
                                                    dialog = PAGE,
                                                    queries = mapOf(
                                                        CITY to "@{$PRIVATE_VARIABLE_CITY}"
                                                    ),
                                                    isEnabledExpression = expression("@{len($PRIVATE_VARIABLE_CITY) > 0}")
                                                ),
                                                navigationAction(
                                                    page = MainScreenController.PAGE,
                                                )
                                            )
                                        )
                                    ),
                                    dismissAction = hideDialogAction(
                                        dialog = PAGE,
                                    )
                                )
                            ),
                        )
                    )
                )
            },
            HttpStatus.OK
        )
    }

    @GetMapping("/$UPDATE")
    fun actionAddCityBottomSheet(
        @RequestParam(name = USER_ID) userId: String,
        @RequestParam(name = CITY) city: String,
    ): ResponseEntity<DivanPatch> {
        return ResponseEntity(
            divanPatch {
                val user = userRepository.findById(userId).getOrNull()!!
                val updatedUser = user.copy(
                    cities = user.cities + city
                )
                userRepository.save(updatedUser)
                patch(
                    changes = listOf(
                        patchChange(
                            id = BOTTOM_SHEET_ID,
                            items = listOf(
                                renderBottomSheet(
                                    items = emptyList(),
                                )
                            ),
                        )
                    ),
                )
            },
            HttpStatus.OK
        )
    }

    @GetMapping("/$HIDE")
    fun hideAddCityBottomSheet(): ResponseEntity<DivanPatch> {
        return ResponseEntity(
            divanPatch {
                patch(
                    changes = listOf(
                        patchChange(
                            id = BOTTOM_SHEET_ID,
                            items = listOf(
                                renderBottomSheet()
                            ),
                        )
                    ),
                )
            },
            HttpStatus.OK
        )
    }

    private fun DivScope.renderBottomSheetContent(
        actions: List<Action>,
    ): Div {
        return column(
            width = matchParentSize(),
            height = wrapContentSize(),
            background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
            alignmentVertical = bottom,
            border = border(
                cornersRadius = cornersRadius(
                    topLeft = 24,
                    topRight = 24,
                )
            ),
            paddings = edgeInsets(all = 16),
            items = listOf(
                renderTitledInput(
                    titleText = Strings.ADD_CITY_BOTTOM_SHEET_TITLE_CITY,
                    keyboardType = single_line_text,
                    hintText = Strings.ADD_CITY_BOTTOM_SHEET_HINT_CITY,
                    textVariable = PRIVATE_VARIABLE_CITY,
                ),
                renderButton(
                    text = Strings.ADD_CITY_BOTTOM_SHEET_BUTTON_TEXT,
                    actions = actions,
                    margins = edgeInsets(top = 24),
                ),
            )
        )
    }

    companion object {
        const val PAGE = "add-city-bottom-sheet"
    }

}