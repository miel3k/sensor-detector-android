package com.pp.iot.de.models.model

data class DeviceMeasurement (
        val deviceId: Int,
        val measurement: String,
        val timestamp: String,
        val unit: String,
        val measurementType: String
)
