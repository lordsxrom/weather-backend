package ru.shumskii.weather.data.repository

import org.springframework.data.repository.CrudRepository
import ru.shumskii.weather.data.entity.User

interface UserRepository : CrudRepository<User, String> {
    fun findByEmail(email: String): User?
}