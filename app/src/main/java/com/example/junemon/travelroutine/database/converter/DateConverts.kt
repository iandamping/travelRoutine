package com.example.junemon.travelroutine.database.converter

import android.arch.persistence.room.TypeConverter
import java.util.*

class DateConverts {

    @TypeConverter
    fun fromTimeStamp(values: Long?): Date? {
        return if (values == null) null else Date(values)
    }

    @TypeConverter
    fun dateToTimeStamp(dates: Date?): Long? {
        return dates?.time
    }
}