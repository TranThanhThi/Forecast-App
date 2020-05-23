package vn.edu.ntu.forecastapp.data.db.current

import androidx.room.Entity
import androidx.room.PrimaryKey
import vn.edu.ntu.forecastapp.data.model.CurrentModel

const val CURRENT_ID = 0
@Entity(tableName = "current_database")
data class CurrentDatabase(
    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_ID,
    val cloudcover: Double,
    val feelslike: Double,
    val humidity: Double,
    val isDay: String,
    val observationTime: String,
    val precip: Double,
    val pressure: Double,
    val temperature: Double,
    val uvIndex: Double,
    val visibility: Double,
    val weatherCode: Double,
    val weatherDescriptions: String,
    val weatherIcons: String,
    val windDegree: Double,
    val windDir: String,
    val windSpeed: Double
)