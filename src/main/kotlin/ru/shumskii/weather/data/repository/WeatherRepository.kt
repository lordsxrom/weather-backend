package ru.shumskii.weather.data.repository

import org.springframework.stereotype.Service
import retrofit2.Response
import ru.shumskii.weather.data.remote.RealtimeWeatherResponse
import ru.shumskii.weather.data.remote.WeatherApiService

@Service
class WeatherRepository(
    private val weatherApiService: WeatherApiService
) {

    fun getRealtimeWeather(
        q: String,
        days: Int,
    ): RealtimeWeatherResponse? {
        val call = weatherApiService.getRealtimeWeather(q, days)
        val response: Response<RealtimeWeatherResponse> = call.execute()
        return if (response.isSuccessful) response.body() else null
    }
}