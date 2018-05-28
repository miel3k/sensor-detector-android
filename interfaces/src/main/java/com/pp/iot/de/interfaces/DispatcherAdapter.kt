package com.pp.iot.de.interfaces

interface DispatcherAdapter {
    fun run(action: suspend () -> Unit)

    operator fun invoke(function: suspend () -> Unit) {
        run(function)
    }
}
