package com.example.junemon.travelroutine.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "personal_tags")
@Parcelize
class PersonalTags(@PrimaryKey(autoGenerate = true) var ID: Int?,
                   @ColumnInfo(name = "input_new_tags") var newTags: String?) : Parcelable {
    constructor() : this(null, null)
}