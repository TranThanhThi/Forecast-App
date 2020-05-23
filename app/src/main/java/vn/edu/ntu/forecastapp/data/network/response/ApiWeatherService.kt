package vn.edu.ntu.forecastapp.data.network.response

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import vn.edu.ntu.forecastapp.data.network.connectivityinterceptor.IConnectivityInterceptor

private const val BASE_URL = "http://api.weatherstack.com/"
private const val API_KEY = "fad34b238ccc865fdecf7579815651c4"


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

//http://api.weatherstack.com/current?access_key=fad34b238ccc865fdecf7579815651c4&query=Ha%20Noi


interface ApiWeatherService {
    @GET("current")
    fun getProperties(@Query("query") location: String,
                      @Query(value = "units") doDo:String
    ): Deferred<CurrentWeatherResponse>

    companion object{
        operator fun invoke(
            iConnectivityInterceptor: IConnectivityInterceptor
        ): ApiWeatherService {
            val requestInterceptor = Interceptor {chain->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key",
                        API_KEY
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient =
                OkHttpClient
                    .Builder()
                    .addInterceptor(requestInterceptor)
                    .addInterceptor(iConnectivityInterceptor)
                    .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(ApiWeatherService::class.java)
        }
    }
}
