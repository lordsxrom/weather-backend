package ru.shumskii.weather.presentation

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.shumskii.weather.data.remote.RealtimeWeatherResponse
import ru.shumskii.weather.data.repository.UserRepository
import ru.shumskii.weather.data.repository.WeatherRepository
import ru.shumskii.weather.presentation.ui_kit.base.*
import ru.shumskii.weather.presentation.ui_kit.resources.Colors
import ru.shumskii.weather.presentation.ui_kit.resources.Images
import ru.shumskii.weather.presentation.ui_kit.resources.Strings
import ru.shumskii.weather.utils.USER_ID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/${MainScreenController.PAGE}")
internal class MainScreenController(
    private val weatherRepository: WeatherRepository,
    private val userRepository: UserRepository,
) {

    @GetMapping
    fun getMainScreen(
        @RequestParam(name = USER_ID) userId: String,
    ): ResponseEntity<DivanPatch> {
        val user = userRepository.findById(userId).getOrNull()!!
        val realtimeWeathers = user.cities.mapNotNull { city ->
            weatherRepository.getRealtimeWeather(
                q = city,
                days = 0,
            )
        }
        return ResponseEntity(
            divanPatch {
                patch(
                    changes = listOf(
                        patchChange(
                            id = ROOT_SCAFFOLD_ID,
                            items = listOf(
                                renderScaffold(
                                    appBar = renderAppBar(
                                        title = Strings.MAIN_APP_BAR_TEXT,
                                    ),
                                    body = renderBody(
                                        realtimeWeathers = realtimeWeathers,
                                    ),
                                    bottomNavigationBar = renderBottomNavigationBar(
                                        stateId = 0,
                                    ),
                                    backgroundColor = solidBackground(color(Colors.SURFACE)).asList(),
                                    floatingActionButton = renderFab(),
                                )
                            )
                        )
                    )
                )
            },
            HttpStatus.OK
        )
    }

    private fun DivScope.renderFab(): Div {
        return container(
            height = fixedSize(56),
            width = fixedSize(56),
            background = solidBackground(color(Colors.TERTIARY)).asList(),
            items = listOf(
                image(
                    height = fixedSize(16),
                    width = fixedSize(16),
                    imageUrl = Url.create(Images.PLUS_ICON),
                    tintColor = color(Colors.SURFACE),
                )
            ),
            alignmentVertical = bottom,
            alignmentHorizontal = end,
            margins = edgeInsets(end = 16, bottom = 24),
            border = border(
                cornerRadius = 28,
            ),
            contentAlignmentVertical = center,
            contentAlignmentHorizontal = center,
            action = showDialogAction(
                dialog = AddCityBottomSheetController.PAGE,
            ),
        )
    }

    fun DivScope.renderBody(
        realtimeWeathers: List<RealtimeWeatherResponse>,
    ): Div {
        return gallery(
            width = matchParentSize(),
            height = matchParentSize(),
            orientation = vertical,
            items = realtimeWeathers.mapIndexed { index, realtimeWeather ->
                renderCityWeather(
                    realtimeWeather = realtimeWeather,
                    isFirst = index == 0,
                    isLast = index == realtimeWeathers.size - 1
                )
            }
        )
    }

    fun DivScope.renderCityWeather(
        realtimeWeather: RealtimeWeatherResponse,
        isFirst: Boolean,
        isLast: Boolean,
    ): Div {
        return stack(
            width = matchParentSize(),
            height = fixedSize(256),
            border = border(
                cornerRadius = 32,
            ),
            background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
            paddings = edgeInsets(all = 16),
            margins = edgeInsets(
                top = if (isFirst) 8 else 4,
                bottom = if (isLast) 8 else 4,
            ),
            items = listOf(
                text(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    text = realtimeWeather.location.name + ", " + realtimeWeather.location.country,
                    fontSize = 22,
                    textColor = color(Colors.ON_SURFACE),
                    alignmentHorizontal = start,
                    alignmentVertical = top,
                ),
                column(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    alignmentHorizontal = start,
                    alignmentVertical = center,
                    contentAlignmentVertical = bottom,
                    items = listOf(
                        text(
                            width = wrapContentSize(),
                            height = wrapContentSize(),
                            text = realtimeWeather.current.temperatureInCelsius.toString() + "°",
                            fontSize = 64,
                            textColor = color(Colors.ON_SURFACE),
                        ),
                        text(
                            width = wrapContentSize(),
                            height = wrapContentSize(),
                            text = "Feels like " + realtimeWeather.current.feelslikeInCelsius + "°",
                            fontSize = 18,
                            textColor = color(Colors.ON_SURFACE),
                        ),
                    )
                ),
                column(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    contentAlignmentHorizontal = center,
                    alignmentHorizontal = end,
                    alignmentVertical = top,
                    items = listOf(
                        image(
                            width = fixedSize(92),
                            height = fixedSize(92),
                            imageUrl = Url.create("http:" + realtimeWeather.current.condition.iconUrl),
                            scale = fit
                        ),
                        text(
                            width = wrapContentSize(),
                            height = wrapContentSize(),
                            text = realtimeWeather.current.condition.text,
                            fontSize = 22,
                            textColor = color(Colors.ON_SURFACE),
                        ),
                    )
                ),
                text(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    text = formatDate(realtimeWeather.location.localtime),
                    fontSize = 22,
                    textColor = color(Colors.ON_SURFACE),
                    alignmentHorizontal = start,
                    alignmentVertical = bottom,
                ),
                column(
                    width = wrapContentSize(),
                    height = wrapContentSize(),
                    contentAlignmentHorizontal = end,
                    alignmentHorizontal = end,
                    alignmentVertical = bottom,
                    items = listOfNotNull(
                        realtimeWeather.forecast.forecastDays.firstOrNull()?.hours?.get(12)?.let { dayCurrent ->
                            text(
                                width = wrapContentSize(),
                                height = wrapContentSize(),
                                text = "Day " + dayCurrent.temperatureInCelsius + "°",
                                fontSize = 18,
                                textColor = color(Colors.ON_SURFACE),
                                fontWeight = bold,
                            )
                        },
                        realtimeWeather.forecast.forecastDays.firstOrNull()?.hours?.get(22)?.let { nightCurrent ->
                            text(
                                width = wrapContentSize(),
                                height = wrapContentSize(),
                                text = "Night " + nightCurrent.temperatureInCelsius + "°",
                                fontSize = 18,
                                textColor = color(Colors.ON_SURFACE),
                                fontWeight = bold,
                            )
                        }
                    )
                ),
            ),
        )
    }

    private fun formatDate(inputDateTimeString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.US)
        val dateTime = LocalDateTime.parse(inputDateTimeString, inputFormatter)
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, HH:mm", Locale.US)
        val formattedDateTimeString = dateTime.format(outputFormatter)
        return formattedDateTimeString
    }

    companion object {
        const val PAGE = "main"
    }

}