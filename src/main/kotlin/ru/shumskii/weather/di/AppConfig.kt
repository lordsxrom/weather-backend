package ru.shumskii.weather.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.shumskii.weather.data.remote.WeatherApiHeadersInterceptor
import ru.shumskii.weather.data.remote.WeatherApiService

@Configuration
class AppConfig {

    @Value("\${rapidapi.weather.key}")
    lateinit var weatherApiKey: String

    @Value("\${rapidapi.weather.base_url}")
    lateinit var weatherBaseUrl: String

    @Bean
    open fun apiKeyInterceptor(): Interceptor {
        return WeatherApiHeadersInterceptor(
            key = weatherApiKey
        )
    }

    @Bean
    open fun okHttpClient(
        weatherApiHeadersInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(weatherApiHeadersInterceptor)
            .build()
    }

    @Bean
    open fun retrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(weatherBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Bean
    open fun weatherApiService(
        retrofit: Retrofit
    ): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

}