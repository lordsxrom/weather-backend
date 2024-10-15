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
import ru.shumskii.weather.utils.CITY
import ru.shumskii.weather.utils.USER_ID
import ru.shumskii.weather.utils.formatDate
import kotlin.math.roundToInt

@RestController
@RequestMapping("/${CityScreenController.PAGE}")
class CityScreenController(
    private val weatherRepository: WeatherRepository,
    private val userRepository: UserRepository,
) {

    @GetMapping
    fun getCityScreen(
        @RequestParam(name = USER_ID) userId: String,
        @RequestParam(name = CITY) city: String,
    ): ResponseEntity<DivanPatch> {
        val realtimeWeather = weatherRepository.getRealtimeWeather(
            q = city,
            days = 10,
        )!!
        return ResponseEntity(
            divanPatch {
                patch(
                    changes = listOf(
                        patchChange(
                            id = ROOT_SCAFFOLD_ID,
                            items = listOf(
                                renderScaffold(
                                    appBar = renderAppBar(
                                        title = realtimeWeather.location.name + ", " + realtimeWeather.location.country,
                                        backButton = renderBackButton(
                                            navigationAction(
                                                page = MainScreenController.PAGE
                                            )
                                        ),
                                    ),
                                    body = renderBody(
                                        realtimeWeather = realtimeWeather,
                                    ),
                                    bottomNavigationBar = renderBottomNavigationBar(
                                        stateId = 0,
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

    private fun DivScope.renderBody(realtimeWeather: RealtimeWeatherResponse): Div {
        return column(
            width = matchParentSize(),
            height = wrapContentSize(),
            items = listOf(
                row(
                    width = matchParentSize(),
                    height = wrapContentSize(),
                    background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
                    paddings = edgeInsets(start = 16, end = 16),
                    items = listOf(
                        text(
                            width = wrapContentSize(),
                            height = wrapContentSize(),
                            text = realtimeWeather.current.temperatureInCelsius.toString() + "°",
                            fontSize = 64,
                            textColor = color(Colors.ON_SURFACE),
                            alignmentVertical = bottom,
                        ),
                        text(
                            width = matchParentSize(),
                            height = wrapContentSize(),
                            text = "Feels like " + realtimeWeather.current.feelslikeInCelsius + "°",
                            fontSize = 18,
                            textColor = color(Colors.ON_SURFACE),
                            alignmentVertical = bottom,
                            margins = edgeInsets(bottom = 8),
                        ),
                        image(
                            width = fixedSize(72),
                            height = fixedSize(72),
                            imageUrl = Url.create("http:" + realtimeWeather.current.condition.iconUrl),
                            scale = fit,
                            alignmentVertical = center
                        ),
                    )
                ),
                state(
                    defaultStateId = "first",
                    divId = "sample",
                    states = listOf(
                        stateItem(
                            stateId = "first",
                            div = column(
                                width = matchParentSize(),
                                height = wrapContentSize(),
                                items = listOf(
                                    row(
                                        width = matchParentSize(),
                                        height = wrapContentSize(),
                                        background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
                                        paddings = edgeInsets(bottom = 13, start = 16, end = 16, top = 16),
                                        items = listOf(
                                            renderButton(
                                                text = "Today",
                                                textColor = color(Colors.ON_TERTIARY),
                                                background = solidBackground(color(Colors.TERTIARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_first",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/first")
                                                    )
                                                )
                                            ),
                                            container(
                                                width = fixedSize(16),
                                            ),
                                            renderButton(
                                                text = "Tomorrow",
                                                textColor = color(Colors.ON_SURFACE),
                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_first",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/second")
                                                    )
                                                )
                                            ),
                                            container(
                                                width = fixedSize(16),
                                            ),
                                            renderButton(
                                                text = "3 days",
                                                textColor = color(Colors.ON_SURFACE),
                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_second",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/third")
                                                    )
                                                )
                                            ),
                                        )
                                    ),
                                    gallery(
                                        width = matchParentSize(),
                                        height = wrapContentSize(),
                                        orientation = vertical,
                                        paddings = edgeInsets(start = 16, end = 16),
                                        items = listOf(
                                            column(
                                                width = matchParentSize(),
                                                height = wrapContentSize(),
                                                margins = edgeInsets(top = 16),
                                                items = listOf(
                                                    row(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        items = listOf(
                                                            renderMeasure(
                                                                iconUrl = "https://cdn-icons-png.flaticon.com/256/11654/11654243.png",
                                                                measure = "Wind speed",
                                                                value = realtimeWeather.current.windInKph.toString() + " km/h"
                                                            ),
                                                            container(
                                                                width = fixedSize(16),
                                                            ),
                                                            renderMeasure(
                                                                iconUrl = "https://files.softicons.com/download/web-icons/vector-stylish-weather-icons-by-bartosz-kaszubowski/png/256x256/cloud.dark.rain.png",
                                                                measure = "Rain chance",
                                                                value = realtimeWeather.forecast.forecastDays[0].day.dailyChanceOfRain.toString() + "%"
                                                            ),
                                                        )
                                                    ),
                                                    row(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        margins = edgeInsets(top = 12),
                                                        items = listOf(
                                                            renderMeasure(
                                                                iconUrl = "https://static-00.iconduck.com/assets.00/pressure-icon-256x256-42io6qn4.png",
                                                                measure = "Pressure",
                                                                value = realtimeWeather.current.pressureMb.toString() + " hpa"
                                                            ),
                                                            container(
                                                                width = fixedSize(16),
                                                            ),
                                                            renderMeasure(
                                                                iconUrl = "https://cdn-icons-png.flaticon.com/256/3262/3262975.png",
                                                                measure = "UV index",
                                                                value = realtimeWeather.current.uv.toString()
                                                            ),
                                                        )
                                                    )
                                                )
                                            ),
                                            column(
                                                width = matchParentSize(),
                                                height = wrapContentSize(),
                                                margins = edgeInsets(top = 16),
                                                paddings = edgeInsets(all = 10),
                                                background = solidBackground(color(Colors.SURFACE_CONTAINER_HIGHEST)).asList(),
                                                border = border(cornerRadius = 16),
                                                items = listOf(
                                                    row(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        items = listOf(
                                                            image(
                                                                height = fixedSize(28),
                                                                width = fixedSize(28),
                                                                imageUrl = Url.create("https://cdn-icons-png.flaticon.com/256/109/109613.png"),
                                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                                border = border(cornerRadius = 14),
                                                                tintColor = color(Colors.ON_SURFACE),
                                                                paddings = edgeInsets(all = 6)
                                                            ),
                                                            text(
                                                                width = wrapContentSize(),
                                                                height = wrapContentSize(),
                                                                text = "Hourly forecast",
                                                                fontSize = 14,
                                                                textColor = color(Colors.ON_SURFACE),
                                                                margins = edgeInsets(start = 8)
                                                            ),
                                                        )
                                                    ),
                                                    gallery(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        orientation = horizontal,
                                                        margins = edgeInsets(top = 16),
                                                        items = realtimeWeather.forecast.forecastDays[0].hours.map { current ->
                                                            column(
                                                                width = fixedSize(40),
                                                                height = wrapContentSize(),
                                                                contentAlignmentHorizontal = center,
                                                                items = listOf(
                                                                    text(
                                                                        width = wrapContentSize(),
                                                                        height = wrapContentSize(),
                                                                        text = formatDate(
                                                                            inputDateTimeString = current.time,
                                                                            inputPattern = "yyyy-MM-dd HH:mm",
                                                                            outputPattern = "HH"
                                                                        ),
                                                                        fontSize = 14,
                                                                        textColor = color(Colors.ON_SURFACE)
                                                                    ),
                                                                    image(
                                                                        width = fixedSize(24),
                                                                        height = fixedSize(32),
                                                                        imageUrl = Url.create("http:" + current.condition.iconUrl),
                                                                        margins = edgeInsets(top = 4)
                                                                    ),
                                                                    text(
                                                                        width = wrapContentSize(),
                                                                        height = wrapContentSize(),
                                                                        text = current.temperatureInCelsius.roundToInt().toString() + "°",
                                                                        fontSize = 20,
                                                                        textColor = color(Colors.ON_SURFACE)
                                                                    ),
                                                                )
                                                            )
                                                        }
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        ),
                        stateItem(
                            stateId = "second",
                            div = column(
                                width = matchParentSize(),
                                height = wrapContentSize(),
                                items = listOf(
                                    row(
                                        width = matchParentSize(),
                                        height = wrapContentSize(),
                                        background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
                                        paddings = edgeInsets(bottom = 13, start = 16, end = 16, top = 16),
                                        items = listOf(
                                            renderButton(
                                                text = "Today",
                                                textColor = color(Colors.ON_SURFACE),
                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_first",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/first")
                                                    )
                                                )
                                            ),
                                            container(
                                                width = fixedSize(16),
                                            ),
                                            renderButton(
                                                text = "Tomorrow",
                                                textColor = color(Colors.ON_TERTIARY),
                                                background = solidBackground(color(Colors.TERTIARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_first",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/second")
                                                    )
                                                )
                                            ),
                                            container(
                                                width = fixedSize(16),
                                            ),
                                            renderButton(
                                                text = "3 days",
                                                textColor = color(Colors.ON_SURFACE),
                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_second",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/third")
                                                    )
                                                )
                                            ),
                                        ),
                                    ),
                                    gallery(
                                        width = matchParentSize(),
                                        height = wrapContentSize(),
                                        orientation = vertical,
                                        paddings = edgeInsets(start = 16, end = 16),
                                        items = listOf(
                                            column(
                                                width = matchParentSize(),
                                                height = wrapContentSize(),
                                                margins = edgeInsets(top = 16),
                                                items = listOf(
                                                    row(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        items = listOf(
                                                            renderMeasure(
                                                                iconUrl = "https://cdn-icons-png.flaticon.com/256/11654/11654243.png",
                                                                measure = "Max wind speed",
                                                                value = realtimeWeather.forecast.forecastDays[1].day.maxWindMph.toString() + " km/h"
                                                            ),
                                                            container(
                                                                width = fixedSize(16),
                                                            ),
                                                            renderMeasure(
                                                                iconUrl = "https://files.softicons.com/download/web-icons/vector-stylish-weather-icons-by-bartosz-kaszubowski/png/256x256/cloud.dark.rain.png",
                                                                measure = "Rain chance",
                                                                value = realtimeWeather.forecast.forecastDays[1].day.dailyChanceOfRain.toString() + "%"
                                                            ),
                                                        )
                                                    ),
                                                    row(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        margins = edgeInsets(top = 12),
                                                        items = listOf(
                                                            renderMeasure(
                                                                iconUrl = "https://cdn-icons-png.flaticon.com/256/727/727790.png",
                                                                measure = "Avg Humidity",
                                                                value = realtimeWeather.forecast.forecastDays[1].day.avgHumidity.toString() + " %"
                                                            ),
                                                            container(
                                                                width = fixedSize(16),
                                                            ),
                                                            renderMeasure(
                                                                iconUrl = "https://cdn-icons-png.flaticon.com/256/3262/3262975.png",
                                                                measure = "UV index",
                                                                value = realtimeWeather.forecast.forecastDays[1].day.uv.toString()
                                                            ),
                                                        )
                                                    )
                                                )
                                            ),
                                            column(
                                                width = matchParentSize(),
                                                height = wrapContentSize(),
                                                margins = edgeInsets(top = 16),
                                                paddings = edgeInsets(all = 10),
                                                background = solidBackground(color(Colors.SURFACE_CONTAINER_HIGHEST)).asList(),
                                                border = border(cornerRadius = 16),
                                                items = listOf(
                                                    row(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        items = listOf(
                                                            image(
                                                                height = fixedSize(28),
                                                                width = fixedSize(28),
                                                                imageUrl = Url.create("https://cdn-icons-png.flaticon.com/256/109/109613.png"),
                                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                                border = border(cornerRadius = 14),
                                                                tintColor = color(Colors.ON_SURFACE),
                                                                paddings = edgeInsets(all = 6)
                                                            ),
                                                            text(
                                                                width = wrapContentSize(),
                                                                height = wrapContentSize(),
                                                                text = "Hourly forecast",
                                                                fontSize = 14,
                                                                textColor = color(Colors.ON_SURFACE),
                                                                margins = edgeInsets(start = 8)
                                                            ),
                                                        )
                                                    ),
                                                    gallery(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        orientation = horizontal,
                                                        margins = edgeInsets(top = 16),
                                                        items = realtimeWeather.forecast.forecastDays[1].hours.map { current ->
                                                            column(
                                                                width = fixedSize(40),
                                                                height = wrapContentSize(),
                                                                contentAlignmentHorizontal = center,
                                                                items = listOf(
                                                                    text(
                                                                        width = wrapContentSize(),
                                                                        height = wrapContentSize(),
                                                                        text = formatDate(
                                                                            inputDateTimeString = current.time,
                                                                            inputPattern = "yyyy-MM-dd HH:mm",
                                                                            outputPattern = "HH"
                                                                        ),
                                                                        fontSize = 14,
                                                                        textColor = color(Colors.ON_SURFACE)
                                                                    ),
                                                                    image(
                                                                        width = fixedSize(24),
                                                                        height = fixedSize(32),
                                                                        imageUrl = Url.create("http:" + current.condition.iconUrl),
                                                                        margins = edgeInsets(top = 4)
                                                                    ),
                                                                    text(
                                                                        width = wrapContentSize(),
                                                                        height = wrapContentSize(),
                                                                        text = current.temperatureInCelsius.roundToInt().toString() + "°",
                                                                        fontSize = 20,
                                                                        textColor = color(Colors.ON_SURFACE)
                                                                    ),
                                                                )
                                                            )
                                                        }
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        ),
                        stateItem(
                            stateId = "third",
                            div = column(
                                width = matchParentSize(),
                                height = wrapContentSize(),
                                items = listOf(
                                    row(
                                        width = matchParentSize(),
                                        height = wrapContentSize(),
                                        background = solidBackground(color(Colors.SURFACE_CONTAINER)).asList(),
                                        paddings = edgeInsets(bottom = 13, start = 16, end = 16, top = 16),
                                        items = listOf(
                                            renderButton(
                                                text = "Today",
                                                textColor = color(Colors.ON_SURFACE),
                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_first",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/first")
                                                    )
                                                )
                                            ),
                                            container(
                                                width = fixedSize(16),
                                            ),
                                            renderButton(
                                                text = "Tomorrow",
                                                textColor = color(Colors.ON_SURFACE),
                                                background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_first",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/second")
                                                    )
                                                )
                                            ),
                                            container(
                                                width = fixedSize(16),
                                            ),
                                            renderButton(
                                                text = "3 days",
                                                textColor = color(Colors.ON_TERTIARY),
                                                background = solidBackground(color(Colors.TERTIARY)).asList(),
                                                actions = listOf(
                                                    action(
                                                        logId = "set_state_second",
                                                        url = Url.create("div-action://set_state?state_id=0/sample/third")
                                                    )
                                                )
                                            ),
                                        ),
                                    ),
                                    gallery(
                                        width = matchParentSize(),
                                        height = wrapContentSize(),
                                        orientation = vertical,
                                        paddings = edgeInsets(start = 16, end = 16),
                                        items = realtimeWeather.forecast.forecastDays.map { day ->
                                            row(
                                                width = matchParentSize(),
                                                height = wrapContentSize(),
                                                margins = edgeInsets(top = 16),
                                                background = solidBackground(color(Colors.SURFACE_CONTAINER_HIGHEST)).asList(),
                                                border = border(cornerRadius = 16),
                                                paddings = edgeInsets(all = 16),
                                                alignmentVertical = center,
                                                items = listOf(
                                                    column(
                                                        width = matchParentSize(),
                                                        height = wrapContentSize(),
                                                        items = listOf(
                                                            text(
                                                                width = wrapContentSize(),
                                                                height = wrapContentSize(),
//                                                                text = formatDate(
//                                                                    inputDateTimeString = day.date,
//                                                                    inputPattern = "yyyy-MM-dd",
//                                                                    outputPattern = "MMMM dd",
//                                                                ),
                                                                text = day.date,
                                                                fontSize = 14,
                                                                textColor = color(Colors.ON_SURFACE),
                                                            ),
                                                            text(
                                                                width = wrapContentSize(),
                                                                height = wrapContentSize(),
                                                                text = day.day.condition.text,
                                                                fontSize = 16,
                                                                textColor = color(Colors.ON_SURFACE_VARIANT),
                                                                margins = edgeInsets(top = 3)
                                                            )
                                                        )
                                                    ),
                                                    column(
                                                        width = wrapContentSize(),
                                                        height = wrapContentSize(),
                                                        items = listOfNotNull(
                                                            text(
                                                                width = wrapContentSize(),
                                                                height = wrapContentSize(),
                                                                text = day.hours[12].temperatureInCelsius.toString() + "°",
                                                                fontSize = 16,
                                                                textColor = color(Colors.ON_SURFACE),
                                                            ),
                                                            text(
                                                                width = wrapContentSize(),
                                                                height = wrapContentSize(),
                                                                text = day.hours[22].temperatureInCelsius.toString() + "°",
                                                                fontSize = 16,
                                                                textColor = color(Colors.ON_SURFACE),
                                                            )
                                                        )
                                                    ),
                                                    container(
                                                        height = fixedSize(51),
                                                        width = fixedSize(1),
                                                        background = solidBackground(color(Colors.SURFACE_VARIANT)).asList(),
                                                        margins = edgeInsets(start = 10)
                                                    ),
                                                    image(
                                                        height = fixedSize(54),
                                                        width = fixedSize(54),
                                                        imageUrl = Url.create("http:" + day.day.condition.iconUrl)
                                                    )
                                                )
                                            )
                                        }
                                    )
                                )
                            )
                        )
                    )
                ),
            ),
        )
    }

    private fun DivScope.renderMeasure(
        iconUrl: String,
        measure: String,
        value: String,
    ): Div {
        return row(
            width = matchParentSize(),
            height = fixedSize(65),
            contentAlignmentVertical = center,
            paddings = edgeInsets(all = 10),
            background = solidBackground(color(Colors.SURFACE_CONTAINER_HIGHEST)).asList(),
            border = border(cornerRadius = 16),
            items = listOf(
                image(
                    height = fixedSize(28),
                    width = fixedSize(28),
                    imageUrl = Url.create(iconUrl),
                    background = solidBackground(color(Colors.ON_PRIMARY)).asList(),
                    border = border(cornerRadius = 14),
                    tintColor = color(Colors.ON_SURFACE),
                    paddings = edgeInsets(all = 6)
                ),
                column(
                    width = matchParentSize(),
                    height = wrapContentSize(),
                    margins = edgeInsets(start = 8),
                    contentAlignmentVertical = center,
                    items = listOf(
                        text(
                            width = wrapContentSize(),
                            height = wrapContentSize(),
                            text = measure,
                            fontSize = 14,
                            textColor = color(Colors.ON_SURFACE),
                        ),
                        text(
                            width = wrapContentSize(),
                            height = wrapContentSize(),
                            text = value,
                            fontSize = 16,
                            textColor = color(Colors.ON_SURFACE),
                        )
                    )
                )
            )
        )
    }

    companion object {
        const val PAGE = "city"
    }
}