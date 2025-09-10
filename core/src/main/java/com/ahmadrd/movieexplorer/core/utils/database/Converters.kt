package com.ahmadrd.movieexplorer.core.utils.database

import androidx.room.TypeConverter
import com.ahmadrd.movieexplorer.core.data.source.remote.response.GenresItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromIntListString(value: String?): List<Int>? {
        return value
            ?.takeIf { it.isNotBlank() }
            ?.split(",")
            ?.mapNotNull { it.toIntOrNull() }
    }

    @TypeConverter
    fun toIntListString(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromStringList(value: String?): List<String>? {
        return value?.split(',')?.map { it.trim() }
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromGenresItemList(value: String?): List<GenresItem>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<GenresItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toGenresItemList(list: List<GenresItem>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
    }
}