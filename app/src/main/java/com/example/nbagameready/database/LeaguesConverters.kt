package com.example.nbagameready.database

import androidx.room.TypeConverter
import com.example.nbagameready.network.nbaapi.Api
import com.example.nbagameready.network.nbaapi.Leagues
import com.example.nbagameready.network.nbaapi.Standard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LeaguesConverters {

    @TypeConverter
    fun fromLeaguesToString(value: Leagues): String {
        val gson = Gson()
        val type = object : TypeToken<Leagues>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringToLeagues(value: String): Leagues {
        val gson = Gson()
        val type = object : TypeToken<Leagues>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStandardToString(value: Standard): String {
        val gson = Gson()
        val type = object : TypeToken<Standard>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringToStandard(value: String): Standard {
        val gson = Gson()
        val type = object : TypeToken<Standard>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromApiToString(value: Api): String {
        val gson = Gson()
        val type = object : TypeToken<Api>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStringToApi(value: String): Api {
        val gson = Gson()
        val type = object : TypeToken<Api>() {}.type
        return gson.fromJson(value, type)
    }
}