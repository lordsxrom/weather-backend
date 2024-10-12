package ru.shumskii.weather.data.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity(name = "user_table")
data class User(
    @Id val id: String = UUID.randomUUID().toString(),
    val email: String,
    val password: String,
    val cities: List<String>,
    val darkTheme: Boolean,
)