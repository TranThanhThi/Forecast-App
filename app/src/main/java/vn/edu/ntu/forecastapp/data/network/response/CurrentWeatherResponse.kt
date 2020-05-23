package vn.edu.ntu.forecastapp.data.network.response


import com.squareup.moshi.JsonClass
import vn.edu.ntu.forecastapp.data.network.response.Current
import vn.edu.ntu.forecastapp.data.network.response.Location
import vn.edu.ntu.forecastapp.data.network.response.Request

@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    val current: Current,
    val location: Location,
    val request: Request
)
