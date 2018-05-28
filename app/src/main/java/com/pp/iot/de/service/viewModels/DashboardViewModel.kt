package com.pp.iot.de.service.viewModels

import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.models.enums.PageIndex

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

    fun onDeviceDataViewButtonClick() {
        navigationManger.navigate(PageIndex.DeviceDataPage)
    }

    fun onServerDataViewButtonClick() {
        navigationManger.navigate(PageIndex.ServerDataPage)
    }
}
