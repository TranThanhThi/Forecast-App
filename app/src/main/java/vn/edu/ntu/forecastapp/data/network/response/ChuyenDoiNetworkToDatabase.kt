package vn.edu.ntu.forecastapp.data.network.response

import vn.edu.ntu.forecastapp.data.db.current.CurrentDatabase
import vn.edu.ntu.forecastapp.data.db.location.LocationDatabase


fun CurrentWeatherResponse.asCurrentDatabase(): CurrentDatabase =
    CurrentDatabase(
        id = 0,
        cloudcover = current.cloudcover * 1.0,
        feelslike = current.feelslike * 1.0,
        humidity = current.humidity * 1.0,
        isDay = current.isDay,
        observationTime = current.observationTime,
        precip = current.precip,
        pressure = current.pressure * 1.0,
        temperature = current.temperature * 1.0,
        uvIndex = current.uvIndex * 1.0,
        visibility = current.visibility * 1.0,
        weatherCode = current.weatherCode * 1.0,
        weatherDescriptions = current.weatherDescriptions.joinToString(),
        weatherIcons = current.weatherIcons.joinToString(),
        windDegree = current.windDegree * 1.0,
        windDir = current.windDir,
        windSpeed = current.windSpeed * 1.0
    )
fun CurrentWeatherResponse.asLocationDatabase():LocationDatabase =
    LocationDatabase(
        country = location.country,
        lat = location.lat.toDouble(),
        localtime = location.localtime,
        localtimeEpoch= location.localtimeEpoch,
        lon = location.lon.toDouble(),
        name = location.name,
        region = location.region,
        timezoneId = location.timezoneId,
        utcOffset = location.utcOffset
    )
