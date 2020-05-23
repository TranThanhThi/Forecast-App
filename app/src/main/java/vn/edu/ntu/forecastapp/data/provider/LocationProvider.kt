package vn.edu.ntu.forecastapp.data.provider

import vn.edu.ntu.forecastapp.data.db.location.LocationDatabase

interface LocationProvider {

    suspend fun hasLocationChanged(locationDatabase: LocationDatabase):Boolean
    suspend fun getPreferencesLocationString():String
}