package vn.edu.ntu.forecastapp.ui.weather.future.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import vn.edu.ntu.forecastapp.R

class DetailWeatherFragment : Fragment() {

    private lateinit var viewModel: DetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DetailWeatherViewModel::class.java)
        return inflater.inflate(R.layout.detail_weather_fragment, container, false)
    }


}
