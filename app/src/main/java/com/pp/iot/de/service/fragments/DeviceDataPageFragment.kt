package com.pp.iot.de.service.fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.pp.iot.de.models.model.Measurement
import com.pp.iot.de.service.R
import com.pp.iot.de.service.viewModels.DeviceDataViewModel
import kotlinx.android.synthetic.main.device_data_page.LightText
import kotlinx.android.synthetic.main.device_data_page.LocationText
import kotlinx.android.synthetic.main.device_data_page.SendDataButton
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class DeviceDataPageFragment : FragmentBase<DeviceDataViewModel>(DeviceDataViewModel::class.java), SensorEventListener {

    override val layoutResourceId: Int
        get() = R.layout.device_data_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
    }

    private lateinit var locationManager: LocationManager

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val measurement = Measurement("7", "" + location.latitude + "," + location.longitude)
            viewModel.gpsMeasurement = measurement
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val lightTable = sensorEvent?.values
        val measurement = Measurement("3", lightTable!![0].toString())
        viewModel.lightMeasurement = measurement
    }

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    override fun initBindings() {
        bindings.add(createBinding(viewModel::gpsMeasurement).subscribe {
                with(LocationText) {
                    text = it.value
                }

        })

        bindings.add(createBinding(viewModel::lightMeasurement).subscribe {
            with(LightText) {
                text = it.value
            }

        })

        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
        } catch (ex: SecurityException) {
            Log.d("DPF", "Security Exception, no location available")
        }

        SendDataButton.setOnClickListener { async(UI) { viewModel.sendMeasurements() } }
    }
}
