package com.example.junemon.travelroutine.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "personal_routines")
@Parcelize
class PersonalRoutines(@PrimaryKey(autoGenerate = true) var ID: Int?,
                       @ColumnInfo(name = "input_routine_description") var description: String?,
                       @ColumnInfo(name = "input_routine") var routine: String?,
                       @ColumnInfo(name = "input_routine_date") var selectedDate: Date?,
                       @ColumnInfo(name = "input_routine_selected_hour") var selectedHour: Int?,
                       @ColumnInfo(name = "input_routine_selected_minute") var selectedMinute: Int?) : Parcelable {
    constructor() : this(null, null, null, null, null, null)
}