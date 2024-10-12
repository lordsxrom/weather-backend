package ru.shumskii.weather.domain.models

import com.fasterxml.jackson.annotation.JsonValue

enum class AuthType(@JsonValue val value: String) {
    SIGN_IN("sign_in"),
    SIGN_UP("sign_up"),
}