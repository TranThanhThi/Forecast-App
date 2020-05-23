package vn.edu.ntu.forecastapp.data.network.connectivityinterceptor

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.Response
import vn.edu.ntu.forecastapp.internal.NoConnectivityException

//Kiem tra ket noi mang
class ConnectivityInterceptor(context: Context) :
    IConnectivityInterceptor {

    private  val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline()){
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

    private fun isOnline():Boolean{
        var result = false // 0: none; 1: mobile data; 2: wifi
        val connectivityManager = appContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
        if(connectivityManager!=null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if(capabilities!=null){
                if(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
                    result = true
            }
        }
        return result
    }
}