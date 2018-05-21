package com.pp.iot.de.service.utils

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

class GsonConvert {
    companion object {
        var gsonInstance = GsonBuilder().create()

        fun <T> serializeObject(obj: T): String {
            return gsonInstance.toJson(obj)
        }

        fun <T> deserializeObject(json: String): T {
            return gsonInstance.fromJson(json)
        }

        fun <T> deserializeObject(json: String, type: Type): T {
            return gsonInstance.fromJson<T>(json, type)
        }

        inline fun <reified T> deserializeObjectInline(json: String): T {
            return gsonInstance.fromJsonInline(json)
        }
    }
}
