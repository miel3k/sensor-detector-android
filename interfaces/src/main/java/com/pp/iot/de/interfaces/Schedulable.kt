package com.pp.iot.de.interfaces

import java.util.Timer

interface Schedulable {
    fun runTask(timer: Timer)
}
