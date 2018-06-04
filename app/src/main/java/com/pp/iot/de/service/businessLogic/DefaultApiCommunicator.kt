package com.pp.iot.de.service.businessLogic

import android.util.Log
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.google.gson.reflect.TypeToken
import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.models.model.Device
import com.pp.iot.de.models.model.ExampleMeasurement
import com.pp.iot.de.models.model.Measurement
import com.pp.iot.de.models.model.MeasurementsList
import com.pp.iot.de.service.utils.GsonConvert

/**
 * Class responsible for communication with backend server.
 */
class DefaultApiCommunicator : ApiCommunicator {

    private val apiClient: FuelManager = FuelManager()
    private val basePath: String = "https://problem-production.herokuapp.com"
    //private val basePath: String = "http://10.0.2.2:8080"

    override suspend fun sendDeviceMeasurements(measurements: List<Measurement>): Boolean {
        apiClient.baseHeaders = mapOf(
                "Content-Type" to "application/json"
        )
        apiClient.basePath = basePath

        val response = apiClient
                .request(
                        Method.POST,
                        "/collect"
                ).body(
                        GsonConvert.serializeObject(
                                MeasurementsList(
                                        measurements
                                )
                        )
                )
                .response()
                .handleResponse()
        Log.i("TAG", response.toString())

        return response.second.statusCode.isSuccessfulStatusCode()
    }

    override suspend fun getMeasurementsForDevice(device: Device)
            : Result<List<ExampleMeasurement>, Exception> {
        apiClient.baseHeaders = mapOf(
                "Content-Type" to "application/json"
        )
        apiClient.basePath = basePath

        val response = apiClient
                .request(
                        Method.GET,
                        "/api/temperatureMeasurement/2"
                )
                .response()

        Log.i("TAG", response.toString())

        response.third.fold({
            return Result.of {
                GsonConvert.deserializeObject<List<ExampleMeasurement>>(
                        response.third.get().toString(Charsets.UTF_8),
                        object : TypeToken<List<ExampleMeasurement>>() {}.type
                )
            }
        }, {
            return Result.error(it)
        })
    }

    override suspend fun getDevices(): Result<List<Device>,Exception> {
        apiClient.baseHeaders = mapOf(
                "Content-Type" to "application/json"
        )
        apiClient.basePath = basePath

        val response = apiClient
                .request(
                        Method.GET,
                        "/api/devices"
                )
                .response()

        Log.i("TAG", response.toString())

        response.third.fold({
            return Result.of {
                GsonConvert.deserializeObject<List<Device>>(
                        response.third.get().toString(Charsets.UTF_8),
                        object : TypeToken<List<Device>>() {}.type
                )
            }
        }, {
            return Result.error(it)
        })
    }

    private fun Triple<Request, Response, Result<ByteArray, FuelError>>.handleResponse(): Triple<Request, Response, Result<ByteArray, FuelError>> {
        when (this.third) {
            is Result.Failure -> {
                Log.i("DAC", second.statusCode.toString())
            }
        }
        return this
    }

    private fun Int.isSuccessfulStatusCode(): Boolean {
        return this in 200..299
    }
}
