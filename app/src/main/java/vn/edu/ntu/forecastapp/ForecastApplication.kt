package vn.edu.ntu.forecastapp

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import vn.edu.ntu.forecastapp.data.network.response.ApiWeatherService
import vn.edu.ntu.forecastapp.data.network.connectivityinterceptor.ConnectivityInterceptor
import vn.edu.ntu.forecastapp.data.network.connectivityinterceptor.IConnectivityInterceptor
import vn.edu.ntu.forecastapp.data.db.DBCurrent
import vn.edu.ntu.forecastapp.data.repository.CurrentRepository
import vn.edu.ntu.forecastapp.data.network.datasource.CurrentWeatherDataSourceImpl
import vn.edu.ntu.forecastapp.data.provider.LocationProvider
import vn.edu.ntu.forecastapp.data.provider.LocationProviderImpl
import vn.edu.ntu.forecastapp.data.provider.UnitProvider
import vn.edu.ntu.forecastapp.data.provider.UnitProviderImpl
import vn.edu.ntu.forecastapp.ui.weather.current.CurrentFactory

class ForecastApplication :Application(),KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        //database
        bind() from singleton { DBCurrent.getInstance(instance()) }
        bind() from singleton { instance<DBCurrent>().currentDao }
        bind() from singleton { instance<DBCurrent>().locationDao }


        bind<IConnectivityInterceptor>() with singleton { ConnectivityInterceptor(instance()) }
        bind() from singleton { ApiWeatherService(instance()) }
        bind() from  singleton { CurrentWeatherDataSourceImpl(instance()) }

        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }

        bind() from   singleton { CurrentRepository(instance(), instance(), instance(), instance()) }

        bind<UnitProvider>()with  singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentFactory(instance(),instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.setting,false)
    }
}