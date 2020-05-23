package vn.edu.ntu.forecastapp.data.repository


import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import vn.edu.ntu.forecastapp.data.network.response.CurrentWeatherResponse
import vn.edu.ntu.forecastapp.data.network.response.asCurrentDatabase
import vn.edu.ntu.forecastapp.data.db.current.CurrentDao
import vn.edu.ntu.forecastapp.data.db.location.LocationDao
import vn.edu.ntu.forecastapp.data.db.location.LocationDatabase
import vn.edu.ntu.forecastapp.data.model.CurrentModel
import vn.edu.ntu.forecastapp.data.network.datasource.CurrentWeatherDataSourceImpl
import vn.edu.ntu.forecastapp.data.network.response.asLocationDatabase
import vn.edu.ntu.forecastapp.data.provider.LocationProvider


class CurrentRepository(private val currentDao: CurrentDao,
                        private val locationDao: LocationDao,
                        private val currentWeatherDataSourceImpl: CurrentWeatherDataSourceImpl,
                        private val locationProvider: LocationProvider
) {
    init {
        currentWeatherDataSourceImpl.downloadCurrentWeather.observeForever{
            persistFetchedCurrentWeather(it)
        }
    }
    //Lay du lieu tu tren network
    private suspend fun initWeather(doDo:String){
        val lastWeatherLocation = locationDao.getLocation().value
        if(lastWeatherLocation == null || locationProvider.hasLocationChanged(lastWeatherLocation)){
            fetchGetWeather(doDo)
            return
        }
        if(isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchGetWeather(doDo)
    }

    private suspend fun fetchGetWeather(doDo:String){
        currentWeatherDataSourceImpl.getWeather(locationProvider.getPreferencesLocationString(),doDo)
    }

    //Lay du lieu model
     suspend fun getCurrentModel(doDo: String):LiveData<CurrentModel>{
        initWeather(doDo)
        return withContext(Dispatchers.IO){
            return@withContext currentDao.getAll()
        }
    }
    suspend fun getLocationModel():LiveData<LocationDatabase>{
        return withContext(Dispatchers.IO){
            return@withContext locationDao.getLocation()
        }
    }

    private fun persistFetchedCurrentWeather(currentWeatherResponse: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentDao.insertAll(currentWeatherResponse.asCurrentDatabase())
            locationDao.insertLocation(currentWeatherResponse.asLocationDatabase())
        }
    }
    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime):Boolean{
        val thirtyMinusAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinusAgo)
    }
}