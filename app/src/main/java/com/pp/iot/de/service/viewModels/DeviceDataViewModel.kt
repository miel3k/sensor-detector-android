package com.pp.iot.de.service.viewModels

import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.interfaces.DispatcherAdapter
import com.pp.iot.de.interfaces.Schedulable
import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.models.model.Measurement
import com.pp.iot.de.service.utils.scheduleAtPeriod
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.util.Timer

class DeviceDataViewModel (private val apiCommunicator: ApiCommunicator,
                           private val navigationManger: NavigationManger,
                           private val dispatcherAdapter: DispatcherAdapter) : ViewModelBase(), Schedulable {
    private val sendMeasurementsPeriod: Long = 60000

    override fun runTask(timer: Timer) {
        timer.scheduleAtPeriod(sendMeasurementsPeriod, {
            dispatcherAdapter.run({ sendMeasurements() })
        })
    }

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
    var lightMeasurement: Measurement by RaisePropertyChangedDelegate(Measurement("", ""))

    suspend fun sendMeasurements(){
        measurementsList.add(gpsMeasurement)
        measurementsList.add(lightMeasurement)

        val res = async(CommonPool) {
            apiCommunicator.sendDeviceMeasurements(
                    measurementsList
            )
        }.await()

        if(res){
            measurementsList = mutableListOf()
        }
    }
}
