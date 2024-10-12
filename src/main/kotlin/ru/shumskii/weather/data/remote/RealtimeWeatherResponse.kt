package ru.shumskii.weather.data.remote

data class RealtimeWeatherResponse(
    val location: Location,
    val current: Current,
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String,
)

data class Current(
    val temp_c: Double,
    val wind_kph: Double,
    val humidity: Int,
)