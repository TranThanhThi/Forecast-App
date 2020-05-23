package vn.edu.ntu.forecastapp.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_current_weather.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import vn.edu.ntu.forecastapp.R
import vn.edu.ntu.forecastapp.data.model.CurrentModel
import vn.edu.ntu.forecastapp.data.provider.UnitSystem
import vn.edu.ntu.forecastapp.ui.base.ScopeFragment

class CurrentWeatherFragment :ScopeFragment(),KodeinAware{

    override val kodein by closestKodein()
    private val currentFactory:CurrentFactory by instance<CurrentFactory>()

    private lateinit var viewModel: CurrentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current_weather,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =ViewModelProvider(this,currentFactory).get(CurrentViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val location = viewModel.location.await()

        currentWeather.observe(viewLifecycleOwner, Observer {
            if(it == null) return@Observer
            group_loading.visibility = View.GONE
            updateDateToday()
            updateCurrent(it)

            when(viewModel.doDo){
                UnitSystem.METRIC -> updateCurrentMETRIC(it)
                UnitSystem.FAHRENHEIT -> updateCurrentFAHRENHEIT(it)
                UnitSystem.SCIENTIFIC -> updateCurrentSCIENTIFIC(it)
            }
        })

        location.observe(viewLifecycleOwner, Observer {
            if(it==null) return@Observer
            updateLocation(it.name)
        })
    }
    private fun updateLocation(location:String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }
    private fun updateDateToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }
    private fun updateCurrent(currentModel: CurrentModel){
        weather_descriptions.text = currentModel.weatherDescriptions
        humidity.setText("Humidity: ${currentModel.humidity}%")
        uv.setText("UV: ${currentModel.uvIndex}")
        Glide.with(this@CurrentWeatherFragment).load(currentModel.weatherIcons).into(weather_icon)
    }
    private fun updateCurrentMETRIC(currentModel: CurrentModel){
        nhietDo.setText("${currentModel.temperature}°C")
        feel_like.setText("Feels like ${currentModel.feelslike}°C")
        wind.setText("Wind: ${currentModel.windDir}, ${currentModel.windSpeed}km/h")
        precipitation.setText("Precipitation: ${currentModel.precip}mm")
        visibility.setText("Visibility: ${currentModel.visibility}km")

    }
    private fun updateCurrentFAHRENHEIT(currentModel: CurrentModel){
        nhietDo.setText("${currentModel.temperature}°F")
        feel_like.setText("Feels like ${currentModel.feelslike}°F")
        wind.setText("Wind: ${currentModel.windDir}, ${currentModel.windSpeed}mph")
        precipitation.setText("Precipitation: ${currentModel.precip}IN")
        visibility.setText("Visibility: ${currentModel.visibility}Miles")
    }
    private fun updateCurrentSCIENTIFIC(currentModel: CurrentModel){
        nhietDo.setText("${currentModel.temperature}°K")
        feel_like.setText("Feels like ${currentModel.feelslike}°K")
        wind.setText("Wind: ${currentModel.windDir}, ${currentModel.windSpeed}km/h")
        precipitation.setText("Precipitation: ${currentModel.precip}mm")
        visibility.setText("Visibility: ${currentModel.visibility}km")
    }
}


