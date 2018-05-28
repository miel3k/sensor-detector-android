package com.pp.iot.de.service.adapters

import com.pp.iot.de.interfaces.DispatcherAdapter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class AndroidDispatcherAdapter : DispatcherAdapter {
    override fun run(action: suspend () -> Unit) {
        async(UI) {
            action()
        }
    }
}
