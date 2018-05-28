package com.pp.iot.de.service.viewModels

import android.util.Log
import com.github.kittinunf.result.Result
import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.models.model.ExampleMeasurement
import com.pp.iot.de.models.model.Measurement
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async

class DashboardViewModel(private val apiCommunicator: ApiCommunicator,
                         private val navigationManger: NavigationManger) : ViewModelBase() {
    /**
     * Meant to be called whenever page becomes active.
     */
    fun navigatedTo() {
        //currently empty, serves as an example for future ViewModels
        //we can begin here any operations that are required by current screen
    }

    var boundText: String by RaisePropertyChangedDelegate("")

    var measurementsList: MutableList<Measurement> by RaisePropertyChangedDelegate(mutableListOf())
    var gpsMeasurement: Measurement by RaisePropertyChangedDelegate(Measurement("", ""))
    var temperatureMeasurement: Measurement by RaisePropertyChangedDelegate(Measurement("", ""))

    var exampleMeasurementsList: List<ExampleMeasurement> by RaisePropertyChangedDelegate(listOf())

    suspend fun sendMeasurements(){
        measurementsList.add(gpsMeasurement)
        measurementsList.add(temperatureMeasurement)

        val res = async(CommonPool) {
            apiCommunicator.sendDeviceMeasurements(
                    measurementsList
            )
        }.await()

        if(res){
            measurementsList = mutableListOf()
        }
    }

    suspend fun getMeasurement() {
        val result = async(CommonPool) {
            apiCommunicator.getExampleMeasurement()
        }.await()

        when (result) {
            is Result.Failure -> {
                Log.e("DVM", result.toString())
            }
            is Result.Success -> {
                exampleMeasurementsList = result.value
                Log.e("DVM", result.toString())
            }
        }
    }
}
