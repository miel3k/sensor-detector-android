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
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.pp.iot.de.models.model.Measurement
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async


class DashboardPageFragment : FragmentBase<DashboardViewModel>(DashboardViewModel::class.java), SensorEventListener {

    override val layoutResourceId: Int
        get() = R.layout.dashboard_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        sensorManager = context?.getSystemService(SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
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
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)
        } catch (ex: SecurityException) {
            Log.d("DPF", "Security Exception, no location available")
        }

        SendLocationButton.setOnClickListener { async(UI) { viewModel.sendMeasurements() } }

        ShowDataButton.setOnClickListener { async(UI) { viewModel.getMeasurement() } }
    }
}
