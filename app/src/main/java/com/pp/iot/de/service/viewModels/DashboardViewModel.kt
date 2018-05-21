package com.pp.iot.de.service.viewModels

import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.interfaces.navigation.NavigationManger
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
    var longitude: Double by RaisePropertyChangedDelegate(-1.0)
    var latitude: Double by RaisePropertyChangedDelegate(-1.0)

    suspend fun sendCurrentLocation(){
        val res = async(CommonPool) {
            apiCommunicator.sendCurrentLocalization(
                    longitude, latitude
            )
        }.await()
    }
}
