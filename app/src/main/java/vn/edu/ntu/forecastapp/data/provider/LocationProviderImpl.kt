package vn.edu.ntu.forecastapp.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import vn.edu.ntu.forecastapp.data.db.location.LocationDatabase
import vn.edu.ntu.forecastapp.internal.LocationPermisstionNotGrantedException
import vn.edu.ntu.forecastapp.internal.asDeferred
import kotlin.Exception

class LocationProviderImpl(context: Context, private val fusedLocationProviderClient: FusedLocationProviderClient) : LocationProvider {

    private val appContext = context.applicationContext
    private val sharedPreferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override suspend fun hasLocationChanged(locationDatabase: LocationDatabase): Boolean {
        val deviceLocationChanged = try {
            hasdevicesLocationChanged(locationDatabase)
        }catch (e:LocationPermisstionNotGrantedException){
            false
        }
        return deviceLocationChanged || hasCustomLocationChanged(locationDatabase)
    }

    override suspend fun getPreferencesLocationString(): String {
        if(isUsingDeviceLocation()){
            try {
                val deviceLocation = getLastDeviceLocation().await()
                    ?: return "${getLocationEdit()}"
                return "${deviceLocation.latitude},${deviceLocation.longitude}"
            }catch (e: LocationPermisstionNotGrantedException){
                return "${getLocationEdit()}"
            }
        }else //su dung vi tri edit
            return "${getLocationEdit()}"
    }
    //Lay vi tri theo thiet bi
    private suspend fun hasdevicesLocationChanged(locationDatabase: LocationDatabase):Boolean{
        if(!isUsingDeviceLocation())
            return false
        val deviceLocation = getLastDeviceLocation().await()
            ?: return false
        val comparisonThreshold = 0.03

        return Math.abs(deviceLocation.latitude - locationDatabase.lat) > comparisonThreshold
                && Math.abs(deviceLocation.longitude - locationDatabase.lon) > comparisonThreshold

    }
    //LAy vi tri theo yeu cau
    private fun hasCustomLocationChanged(locationDatabase: LocationDatabase):Boolean{
        val customLocationName = getLocationEdit()
        return customLocationName != locationDatabase.name
    }
    //Su dung vi tri cua thiet bi
    //Lay ra o xml
    private fun isUsingDeviceLocation():Boolean = sharedPreferences.getBoolean("USE_DEVICE_LOCATION",true)
    private fun getLocationEdit():String? = sharedPreferences.getString("CUSTOM_LOCATION",null)

    //Lay ra vi tri cuoi cung
    private fun getLastDeviceLocation():Deferred<Location?>{
        return if(hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermisstionNotGrantedException()
    }

    //Kiem tra cho phep use vi tri chua
    private fun hasLocationPermission():Boolean{
        return ContextCompat.checkSelfPermission(appContext,
        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
}