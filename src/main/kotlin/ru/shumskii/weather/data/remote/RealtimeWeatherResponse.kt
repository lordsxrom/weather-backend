package ru.shumskii.weather.data.remote

import com.google.gson.annotations.SerializedName

data class RealtimeWeatherResponse(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current,
    @SerializedName("forecast") val forecast: Forecast,
)

data class Location(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("tz_id") val tzId: String,
    @SerializedName("localtime_epoch") val localtimeEpoch: Long,
    @SerializedName("localtime") val localtime: String,
)

data class Current(
    @SerializedName("temp_c") val temperatureInCelsius: Double,
    @SerializedName("wind_kph") val windInKph: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("feelslike_c") val feelslikeInCelsius: Double,
    @SerializedName("condition") val condition: Condition,
)

data class Condition(
    @SerializedName("text") val text: String,
    @SerializedName("icon") val iconUrl: String,
    @SerializedName("code") val code: Long,
)

data class Forecast(
    @SerializedName("forecastday") val forecastDays: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("date_epoch") val dateEpoch: Long,
    @SerializedName("hour") val hours: List<Current>,
)