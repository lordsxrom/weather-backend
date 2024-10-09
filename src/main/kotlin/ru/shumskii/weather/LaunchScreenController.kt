package ru.shumskii.weather

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
    ): ResponseEntity<Any> {
        return ResponseEntity(
            Any(),
            HttpStatus.OK
        )
    }

    companion object {
        const val PAGE = "launch"
    }
}