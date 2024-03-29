package com.pp.iot.de.interfaces

import com.github.kittinunf.result.Result
import com.pp.iot.de.models.model.Device
import com.pp.iot.de.models.model.DeviceMeasurement
import com.pp.iot.de.models.model.Measurement

interface ApiCommunicator {
    suspend fun sendDeviceMeasurements(measurements: List<Measurement>) : Boolean
    suspend fun getDevices(): Result<List<Device>, Exception>
    suspend fun getMeasurementsForDevice(device: Device): Result<List<DeviceMeasurement>, Exception>
}
