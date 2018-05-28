package com.pp.iot.de.service.viewModels

import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.models.enums.PageIndex
import com.pp.iot.de.service.businessLogic.TaskScheduler

/**
 *  "Root" view model meant to act as a startup one. Performs first managed navigation.
 *   Can be associated as a backing object of main activity.
 */
class MainViewModel(private val navigationManger: NavigationManger,
                    private val taskScheduler: TaskScheduler
                    ) {

    fun initialized() {
        taskScheduler.executeTasks()
        navigationManger.navigate(PageIndex.MainPage)
    }
}
