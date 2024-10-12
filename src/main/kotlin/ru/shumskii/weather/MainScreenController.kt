package ru.shumskii.weather

import divkit.dsl.*
import divkit.dsl.scope.DivScope
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.shumskii.weather.data.remote.RealtimeWeatherResponse
import ru.shumskii.weather.data.repository.UserRepository
import ru.shumskii.weather.data.repository.WeatherRepository
import ru.shumskii.weather.ui.*
import ru.shumskii.weather.utils.USER_ID
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
            weatherRepository.getRealtimeWeather(city)
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
                                        title = "Main",
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
//            action = showBottomSheetAction(
//                host = host,
//                bottomSheetId = AddCityBottomSheetScreenController.PAGE,
//                queries = mapOf(
//                    "email" to email,
//                )
//            )
        )
    }

    fun DivScope.renderBody(
        realtimeWeathers: List<RealtimeWeatherResponse>,
    ): Div {
        return gallery(
            width = matchParentSize(),
            height = matchParentSize(),
            orientation = vertical,
            margins = edgeInsets(16),
            itemSpacing = 16,
            items = realtimeWeathers.map { realtimeWeather ->
                column(
                    width = matchParentSize(),
                    height = wrapContentSize(),
                    border = border(
                        cornerRadius = 16,
                    ),
//                    action = navigationAction(
//                        host = host,
//                        pageId = ItemScreenController.PAGE,
//                        queries = mapOf(
//                            "email" to email,
//                            "city" to realtimeWeather.location.name,
//                        )
//                    ),
                    background = solidBackground(color(Colors.SURFACE_CONTAINER_HIGHEST)).asList(),
                    paddings = edgeInsets(all = 16),
                    items = listOf(
                        text(realtimeWeather.location.name),
                        text(realtimeWeather.location.country),
                        text(realtimeWeather.location.localtime),
                        text(realtimeWeather.location.lat.toString()),
                        text(realtimeWeather.location.lon.toString()),
                    ),
                )
            }
        )
    }

    companion object {
        const val PAGE = "main"
    }

}