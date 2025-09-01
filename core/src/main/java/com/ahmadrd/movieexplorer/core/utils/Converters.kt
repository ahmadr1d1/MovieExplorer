package com.ahmadrd.movieexplorer.core.utils

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromIntListString(value: String?): List<Int>? {
        return value?.split(',')?.mapNotNull {
            try {
                it.toInt()
            } catch (e: NumberFormatException) {
                null
            }
        }
    }

    @TypeConverter
    fun toIntListString(list: List<Int>?): String? {
        return list?.joinToString(",")
    }
}
