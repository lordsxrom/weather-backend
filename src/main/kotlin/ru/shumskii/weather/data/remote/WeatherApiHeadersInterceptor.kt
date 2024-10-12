package ru.shumskii.weather.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class WeatherApiHeadersInterceptor(private val key: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("x-rapidapi-key", key)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
