package com.pp.iot.de.service.composition

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.pp.iot.de.service.businessLogic.DefaultApiCommunicator
import com.pp.iot.de.service.viewModels.DashboardViewModel
import com.pp.iot.de.service.viewModels.MainViewModel
import com.pp.iot.de.interfaces.ApiCommunicator
import pw.kmp.kodeinject.injectedSingleton

fun Kodein.registerDependencies(): Kodein {
    return Kodein {
        extend(this@registerDependencies)
        bind<ApiCommunicator>() with injectedSingleton<DefaultApiCommunicator>()
    }
}

fun Kodein.registerViewModels(): Kodein {
    return Kodein {
        extend(this@registerViewModels)
        bind<MainViewModel>() with injectedSingleton()
        bind<DashboardViewModel>() with injectedSingleton()
    }
}