package ru.shumskii.weather.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.shumskii.weather.data.remote.RealtimeWeatherResponse

interface WeatherApiService {
    @GET("current.json")
    fun getRealtimeWeather(
        @Query("q") q: String
    ): Call<RealtimeWeatherResponse>
}
