package com.example.junemon.travelroutine.network.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class PersonalNews(val articles: List<Article>?) {
    @Parcelize
    @Entity(tableName = "personal_news")
    data class Article(@PrimaryKey(autoGenerate = true) var ID: Int?,
                       @ColumnInfo(name = "author") var author: String?,
                       @ColumnInfo(name = "title") var title: String?,
                       @ColumnInfo(name = "description") var description: String?,
                       @ColumnInfo(name = "url") var url: String?,
                       @ColumnInfo(name = "urlToImage") var urlToImage: String?,
                       @ColumnInfo(name = "publishedAt") var publishedAt: String?) : Parcelable {
        constructor() : this(null, null, null, null,
                null, null, null)

    }
}
