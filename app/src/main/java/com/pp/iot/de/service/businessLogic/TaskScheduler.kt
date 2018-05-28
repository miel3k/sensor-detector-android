package com.pp.iot.de.service.businessLogic

import com.pp.iot.de.interfaces.Schedulable
import java.util.Timer

class TaskScheduler(private val schedulableViewModelsSet: Set<Schedulable>) {
    private val timer = Timer()

    fun executeTasks() {
        schedulableViewModelsSet.forEach { it.runTask(timer) }
    }
}
