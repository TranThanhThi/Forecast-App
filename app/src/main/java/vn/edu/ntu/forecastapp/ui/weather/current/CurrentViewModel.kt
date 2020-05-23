package vn.edu.ntu.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel
import vn.edu.ntu.forecastapp.data.provider.UnitProvider
import vn.edu.ntu.forecastapp.data.provider.UnitSystem
import vn.edu.ntu.forecastapp.data.repository.CurrentRepository
import vn.edu.ntu.forecastapp.internal.lazyDeferred


class CurrentViewModel(private val currentRepository: CurrentRepository,private val unitProvider: UnitProvider) : ViewModel() {
     val doDo = unitProvider.getUnitSystem()

    val weather by lazyDeferred {
        when(doDo){
            UnitSystem.METRIC ->currentRepository.getCurrentModel("m")
            UnitSystem.FAHRENHEIT ->currentRepository.getCurrentModel("f")
            UnitSystem.SCIENTIFIC -> currentRepository.getCurrentModel("s")
        }

    }
    val location by lazyDeferred {
        currentRepository.getLocationModel()
    }
}