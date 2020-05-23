package vn.edu.ntu.forecastapp.data.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    val language: String,
    val query: String,
    val type: String,
    val unit: String
)