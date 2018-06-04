package com.pp.iot.de.service.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.salomonbrys.kodein.instance
import com.pp.iot.de.service.App
import com.pp.iot.de.service.R
import com.pp.iot.de.service.navigation.CachedPageProvider
import com.pp.iot.de.service.navigation.DefaultNavigationManager
import com.pp.iot.de.service.viewModels.MainViewModel
import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.models.enums.PageIndex
import com.pp.iot.de.service.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var navigationManager: NavigationManger
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigationManager = DefaultNavigationManager(
                mapOf(
                        PageIndex.MainPage to CachedPageProvider<NavigationFragmentBase>({ DashboardPageFragment() }),
                        PageIndex.DeviceDataPage to CachedPageProvider<NavigationFragmentBase>({ DeviceDataPageFragment() }),
                        PageIndex.ServerDataPage to CachedPageProvider<NavigationFragmentBase>({ ServerDataPageFragment() }),
                        PageIndex.DeviceMeasurementsPage to CachedPageProvider<NavigationFragmentBase>({ DeviceMeasurementsPageFragment() })
                ),
                RootView,
                supportFragmentManager
        )

        App.kodeinContainer.instance<MainViewModel>().initialized()
    }

    override fun onBackPressed() {
        //our activity isn't meant for destruction thus we are moving it back upon reaching end of stack
        //TODO Not sure why singleInstance in manifest isn't taking care of this. Need research.
        if (supportFragmentManager.backStackEntryCount == 0) {
            moveTaskToBack(true)
            return
        }
        super.onBackPressed()

        navigationManager.wentBack()
    }
}
