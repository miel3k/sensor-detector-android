package com.pp.iot.de.service.composition

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.inSet
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.setBinding
import com.pp.iot.de.service.businessLogic.DefaultApiCommunicator
import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.interfaces.DispatcherAdapter
import com.pp.iot.de.interfaces.Schedulable
import com.pp.iot.de.service.adapters.AndroidDispatcherAdapter
import com.pp.iot.de.service.businessLogic.TaskScheduler
import com.pp.iot.de.service.viewModels.*
import pw.kmp.kodeinject.injectedSingleton

fun Kodein.registerDependencies(): Kodein {
    return Kodein {
        extend(this@registerDependencies)
        bind<ApiCommunicator>() with injectedSingleton<DefaultApiCommunicator>()
        bind<DispatcherAdapter>() with injectedSingleton<AndroidDispatcherAdapter>()

        bind() from setBinding<Schedulable>()
        bind<Schedulable>().inSet() with provider { instance<DeviceDataViewModel>() }
        bind<Schedulable>().inSet() with provider { instance<ServerDataViewModel>() }
        bind<Schedulable>().inSet() with provider { instance<DeviceMeasurementsViewModel>() }
        bind<TaskScheduler>() with provider { TaskScheduler(instance()) }
    }
}

fun Kodein.registerViewModels(): Kodein {
    return Kodein {
        extend(this@registerViewModels)
        bind<MainViewModel>() with injectedSingleton()
        bind<DashboardViewModel>() with injectedSingleton()
        bind<DeviceDataViewModel>() with injectedSingleton()
        bind<ServerDataViewModel>() with injectedSingleton()
        bind<DeviceMeasurementsViewModel>() with injectedSingleton()
    }
}
