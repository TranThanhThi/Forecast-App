package vn.edu.ntu.forecastapp.data.model

import androidx.room.ColumnInfo

data class CurrentModel(
    @ColumnInfo(name="cloudcover")
    val cloudcover: Double,
    @ColumnInfo(name = "feelslike")
    val feelslike: Double,
    @ColumnInfo(name = "humidity")
    val humidity: Double,
    @ColumnInfo(name = "isDay")
    val isDay: String,
    @ColumnInfo(name = "observationTime")
    val observationTime: String,
    @ColumnInfo(name = "precip")
    val precip: Double,
    @ColumnInfo(name = "pressure")
    val pressure: Double,
    @ColumnInfo(name = "temperature")
    val temperature: Double,
    @ColumnInfo(name = "uvIndex")
    val uvIndex: Double,
    @ColumnInfo(name = "visibility")
    val visibility: Double,
    @ColumnInfo(name = "weatherCode")
    val weatherCode: Double,
    @ColumnInfo(name = "weatherDescriptions")
    val weatherDescriptions: String,
    @ColumnInfo(name = "weatherIcons")
    val weatherIcons: String,
    @ColumnInfo(name = "windDegree")
    val windDegree: Double,
    @ColumnInfo(name = "windDir")
    val windDir: String,
    @ColumnInfo(name = "windSpeed")
    val windSpeed: Double
){
    @ColumnInfo(name = "id")
    var id:Int = 0
}