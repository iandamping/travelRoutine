package com.example.junemon.travelroutine.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "personal_belongings")
data class PersonalItems(@PrimaryKey(autoGenerate = true) var ID: Int?,
                         @ColumnInfo(name = "input_item") var items: String?,
                         @ColumnInfo(name = "input_destination") var destination: String?,
                         @ColumnInfo(name = "input_departure_date") var selectedDate: Date?,
                         @ColumnInfo(name = "input_arrival_selected_hour") var selectedHour: Int?,
                         @ColumnInfo(name = "input_arrival_selected_minute") var selectedMinute: Int?) : Parcelable {
    constructor() : this(null, null, null, null,
            null, null)

}