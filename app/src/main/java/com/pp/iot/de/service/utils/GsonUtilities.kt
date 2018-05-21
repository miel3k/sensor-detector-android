package com.pp.iot.de.service.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun <T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T> Gson.fromJsonInline(json: String): T =
        this.fromJson<T>(json, object : TypeToken<T>() {}.type)
