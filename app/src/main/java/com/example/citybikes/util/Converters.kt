package com.example.citybikes.util

import androidx.room.TypeConverter
import com.example.citybikes.model.Location
import com.google.gson.Gson
import java.lang.reflect.Type

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): Location {
        return gson.fromJson(json, Location::class.java)
    }

    @TypeConverter
    fun toJson(obj: Location): String {
        return gson.toJson(obj)
    }

    @TypeConverter
    fun fromAny(json: String): Any {
        return gson.fromJson(json, Location::class.java)
    }

    @TypeConverter
    fun toAny(obj: Any): String {
        return gson.toJson(obj)
    }
}