package ru.shumskii.weather

import divkit.dsl.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/${LaunchScreenController.PAGE}")
class LaunchScreenController {

    @GetMapping
    fun getLaunchScreen(
        @RequestHeader headers: Map<String, String>
    ): ResponseEntity<Divan> {
        return ResponseEntity(
            divan {
                data(
                    logId = PAGE,
                    div = container(
                        height = matchParentSize(),
                        width = matchParentSize(),
                        contentAlignmentHorizontal = center,
                        contentAlignmentVertical = center,
                        items = listOf(
                            text(
                                height = wrapContentSize(),
                                width = wrapContentSize(),
                                text = "Hello DivKit",
                            )
                        )
                    ),
                )
            },
            HttpStatus.OK
        )
    }

    companion object {
        const val PAGE = "launch"
    }
}