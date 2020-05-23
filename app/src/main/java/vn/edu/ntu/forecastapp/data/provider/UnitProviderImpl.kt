package vn.edu.ntu.forecastapp.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext
    private val sharedPreferences:SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitSystem(): UnitSystem {
        val selectName = sharedPreferences.getString("UNIT_SYSTEM",UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectName!!)
    }
}