package com.pp.iot.de.service.fragments

import android.content.Context.LOCATION_SERVICE
import android.location.Location
import com.pp.iot.de.service.R
import com.pp.iot.de.service.viewModels.DashboardViewModel
import kotlinx.android.synthetic.main.dashboard_page.*
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.bluetooth.BluetoothAdapter
import android.content.Context.SENSOR_SERVICE
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class DashboardPageFragment : FragmentBase<DashboardViewModel>(DashboardViewModel::class.java) {

    override val layoutResourceId: Int
        get() = R.layout.dashboard_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager

        activity?.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND))
        //sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private var locationManager: LocationManager? = null

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            viewModel.longitude = location.longitude
            viewModel.latitude = location.latitude
            LocationText.text = "" + location.longitude + ":" + location.latitude
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val BTAdapter = BluetoothAdapter.getDefaultAdapter()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, java.lang.Short.MIN_VALUE)
                val name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME)
                LocationText.text = "" + LocationText.text + name + " => " + rssi + "dBm\n"
            }
        }
    }

    /*override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val temperatureTable = sensorEvent?.values
        val temperature = temperatureTable!![0]
    }*/

    //private val sensorManager: SensorManager = context?.getSystemService(SENSOR_SERVICE) as SensorManager
    //private val sensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)

    override fun initBindings() {
        LocationButton.setOnClickListener { _ ->
            try {
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
            } catch (ex: SecurityException) {
                Log.d("DPF", "Security Exception, no location available")
            }
        }

        BluetoothDevicesButton.setOnClickListener { _ -> BTAdapter.startDiscovery() }

        SendLocationButton.setOnClickListener { async(UI) { viewModel.sendCurrentLocation() } }
    }
}
