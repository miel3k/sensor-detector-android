package com.pp.iot.de.interfaces

interface ApiCommunicator {
    suspend fun sendCurrentLocalization(longitude: Double, latitude: Double) : Boolean
    suspend fun sendDeviceTemperature(temperature: Float) : Boolean
}
