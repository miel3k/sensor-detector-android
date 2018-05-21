package com.pp.iot.de.service.viewModels

import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.models.enums.PageIndex

/**
 *  "Root" view model meant to act as a startup one. Performs first managed navigation.
 *   Can be associated as a backing object of main activity.
 */
class MainViewModel(private val navigationManger: NavigationManger) {

    fun initialized() {
        navigationManger.navigate(PageIndex.MainPage)
    }
}