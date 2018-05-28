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
import kotlinx.android.synthetic.main.device_data_page.LocationText
import kotlinx.android.synthetic.main.device_data_page.SendLocationButton
import kotlinx.android.synthetic.main.device_data_page.TemperatureText
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
            val measurement = Measurement("7", "" + location.longitude + "," + location.longitude)
            viewModel.gpsMeasurement = measurement
            LocationText.text = "" + location.longitude + "," + location.latitude
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        val temperatureTable = sensorEvent?.values
        val measurement = Measurement("1", temperatureTable!![0].toString())
        viewModel.temperatureMeasurement = measurement
        TemperatureText.text = temperatureTable[0].toString()
    }

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor

    override fun initBindings() {
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        sensorManager = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        } catch (ex: SecurityException) {
            Log.d("DPF", "Security Exception, no location available")
        }

        SendLocationButton.setOnClickListener { async(UI) { viewModel.sendMeasurements() } }
    }
}
