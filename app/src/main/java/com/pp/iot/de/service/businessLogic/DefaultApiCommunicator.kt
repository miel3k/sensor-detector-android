package com.pp.iot.de.service.businessLogic

import android.util.Log
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.pp.iot.de.interfaces.ApiCommunicator
import com.pp.iot.de.models.model.Location
import com.pp.iot.de.models.model.Temperature
import com.pp.iot.de.service.utils.GsonConvert

/**
 * Class responsible for communication with backend server.
 */
class DefaultApiCommunicator : ApiCommunicator {
    private val apiClient: FuelManager = FuelManager()
    private val basePath: String = ""

    override suspend fun getDataFromServer(): Boolean {
//        apiClient.baseHeaders = mapOf(
//                "Content-Type" to "application/json"
//        )
//
//        val response = apiClient
//                .request(
//                        Method.GET,
//                        basePath
//                ).body(
//                        GsonConvert.serializeObject(
//                                Temperature(
//                                        temperature
//                                )
//                        )
//                )
//                .response()
//                .handleResponse()
//
//        return response.second.statusCode.isSuccessfulStatusCode()
        return true
    }

    override suspend fun sendDeviceTemperature(temperature: Float): Boolean {
        apiClient.baseHeaders = mapOf(
                "Content-Type" to "application/json"
        )

        val response = apiClient
                .request(
                        Method.GET,
                        basePath
                ).body(
                        GsonConvert.serializeObject(
                                Temperature(
                                        temperature
                                )
                        )
                )
                .response()
                .handleResponse()

        return response.second.statusCode.isSuccessfulStatusCode()
    }

    override suspend fun sendCurrentLocalization(longitude: Double, latitude: Double): Boolean {
        apiClient.baseHeaders = mapOf(
                "Content-Type" to "application/json"
        )

        val response = apiClient
                .request(
                        Method.GET,
                        basePath
                ).body(
                        GsonConvert.serializeObject(
                                Location(
                                        longitude,
                                        latitude
                                )
                        )
                )
                .response()
                .handleResponse()

        return response.second.statusCode.isSuccessfulStatusCode()
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
