package com.pp.iot.de.interfaces

import com.github.kittinunf.result.Result
import com.pp.iot.de.models.model.ExampleMeasurement
import com.pp.iot.de.models.model.Measurement

interface ApiCommunicator {
    suspend fun sendDeviceMeasurements(measurements: List<Measurement>) : Boolean
    suspend fun getExampleMeasurement(): Result<List<ExampleMeasurement>, Exception>
}
