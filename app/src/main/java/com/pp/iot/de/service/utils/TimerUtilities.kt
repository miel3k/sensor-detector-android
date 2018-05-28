package com.pp.iot.de.service.utils

import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask

fun Timer.scheduleAtPeriod(period: Long, action: TimerTask.() -> Unit): TimerTask {
    val task = timerTask(action)
    schedule(task, 0, period)
    return task
}
