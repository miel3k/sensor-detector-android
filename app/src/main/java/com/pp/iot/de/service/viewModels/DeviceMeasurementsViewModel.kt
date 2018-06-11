package com.pp.iot.de.service.viewModels

import android.util.Log
import com.github.kittinunf.result.Result
import com.github.salomonbrys.kodein.instance
import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.interfaces.DispatcherAdapter
import com.pp.iot.de.interfaces.Schedulable
import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.models.model.Device
import com.pp.iot.de.models.model.DeviceMeasurement
import com.pp.iot.de.service.App
import com.pp.iot.de.service.utils.scheduleAtPeriod
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import java.util.Timer

class DeviceMeasurementsViewModel (private val apiCommunicator: ApiCommunicator,
                                   private val navigationManger: NavigationManger,
                                   private val dispatcherAdapter: DispatcherAdapter) : ViewModelBase(), Schedulable {
    private val getDataFromServerPeriod: Long = 60000

    override fun runTask(timer: Timer) {
        timer.scheduleAtPeriod(getDataFromServerPeriod, {
            dispatcherAdapter.run({ getMeasurements() })
        })
    }

    /**
     * Meant to be called whenever page becomes active.
     */
    fun navigatedTo() {
        selectedDevice = App.kodeinContainer.kodein.instance<ServerDataViewModel>().selectedDevice
        async(UI) {
            getMeasurements()
        }
    }

    var measurementsList: List<DeviceMeasurement> by RaisePropertyChangedDelegate(listOf())
    lateinit var selectedDevice: Device

    suspend fun getMeasurements() {
        val result = async(CommonPool) {
            apiCommunicator.getMeasurementsForDevice(selectedDevice)
        }.await()

        when (result) {
            is Result.Failure -> {
                Log.e("DVM", result.toString())
            }
            is Result.Success -> {
                measurementsList = result.value
                Log.e("DVM", result.toString())
            }
        }
    }
}
