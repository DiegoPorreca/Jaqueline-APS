package com.example.myapplicationgemini.data.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de resposta genérica da API intermediária
 * Esta classe representa a resposta que vem da nossa API intermediária
 */
data class ApiResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("data")
    val data: Any?, // Pode ser qualquer tipo de dado da API externa
    
    @SerializedName("message")
    val message: String?,
    
    @SerializedName("timestamp")
    val timestamp: String?
)

/**
 * Modelo de resposta de exemplo para API de clima (OpenWeather)
 * Este é um exemplo de como os dados podem ser estruturados
 */
data class WeatherData(
    @SerializedName("name")
    val cityName: String?,
    
    @SerializedName("main")
    val main: MainWeather?,
    
    @SerializedName("weather")
    val weather: List<Weather>?,
    
    @SerializedName("wind")
    val wind: Wind?
)

data class MainWeather(
    @SerializedName("temp")
    val temperature: Double?,
    
    @SerializedName("feels_like")
    val feelsLike: Double?,
    
    @SerializedName("humidity")
    val humidity: Int?,
    
    @SerializedName("pressure")
    val pressure: Int?
)

data class Weather(
    @SerializedName("main")
    val main: String?,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("icon")
    val icon: String?
)

data class Wind(
    @SerializedName("speed")
    val speed: Double?
)


