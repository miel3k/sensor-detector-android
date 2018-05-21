package com.pp.iot.de.service.viewModels

import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.interfaces.navigation.NavigationManger

class DashboardViewModel(private val apiCommunicator: ApiCommunicator,
                         private val navigationManger: NavigationManger) : ViewModelBase() {
    /**
     * Meant to be called whenever page becomes active.
     */
    fun navigatedTo() {
        //currently empty, serves as an example for future ViewModels
        //we can begin here any operations that are required by current screen
    }

    //TODO Think about commands?
    //TODO Add test api call here.
    fun onHelloButtonClick() {
        helloText = "Hello"
    }

    var helloText: String by RaisePropertyChangedDelegate("")
    var boundText: String by RaisePropertyChangedDelegate("")
}