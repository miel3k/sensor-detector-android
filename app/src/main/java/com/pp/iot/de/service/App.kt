package com.pp.iot.de.service

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.provider
import com.pp.iot.de.service.activities.MainActivity
import com.pp.iot.de.service.composition.registerDependencies
import com.pp.iot.de.service.composition.registerViewModels
import com.pp.iot.de.interfaces.navigation.NavigationManger


class App : Application() {
    companion object {
        lateinit var kodeinContainer: Kodein
            private set
    }

    override fun onCreate() {
        super.onCreate()

        val kodein = Kodein {}.registerDependencies().registerViewModels()
        //as our navigationManger has to be created with first activity we are registering delegate
        kodeinContainer = Kodein.invoke {
            extend(kodein)
            bind<NavigationManger>() with provider { MainActivity.navigationManager }
        }
    }
}


