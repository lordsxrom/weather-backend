package ru.shumskii.weather.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast.json")
    fun getRealtimeWeather(
        @Query("q") q: String,
        @Query("days") days: Int,
    ): Call<RealtimeWeatherResponse>
}
