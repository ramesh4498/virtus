package com.virtus.demo.hits.utils

import androidx.room.TypeConverter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimestampConverter {
    var df: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")

    @TypeConverter
    fun fromDate(date: String): Date? {
        try {
            return df.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }

    @TypeConverter
    fun toDate(value: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        return format.format(value)
    }

}