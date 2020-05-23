package vn.edu.ntu.forecastapp.data.network.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.edu.ntu.forecastapp.data.network.response.ApiWeatherService
import vn.edu.ntu.forecastapp.data.network.response.CurrentWeatherResponse
import vn.edu.ntu.forecastapp.internal.NoConnectivityException

class CurrentWeatherDataSourceImpl(val apiWeatherService: ApiWeatherService) {

    private val _downloadCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    val downloadCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadCurrentWeather

     suspend fun getWeather(location:String,doDo:String) {
         withContext(Dispatchers.Main) {
             try {
                 val data = apiWeatherService.getProperties(location,doDo).await()
                 _downloadCurrentWeather.value = data
             } catch (e: NoConnectivityException) {
                 Log.e("CKN", "No connect Internet", e)
             }
         }
    }
}