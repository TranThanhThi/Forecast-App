package vn.edu.ntu.forecastapp.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vn.edu.ntu.forecastapp.data.provider.UnitProvider
import vn.edu.ntu.forecastapp.data.repository.CurrentRepository

class CurrentFactory (private val currentRepository: CurrentRepository,private val unitProvider: UnitProvider):ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentViewModel(currentRepository,unitProvider) as T
    }

}