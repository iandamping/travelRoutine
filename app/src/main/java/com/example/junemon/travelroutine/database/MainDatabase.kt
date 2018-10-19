package com.example.junemon.travelroutine.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.junemon.travelroutine.database.converter.DateConverts
import com.example.junemon.travelroutine.database.dao.DaoPersonalEvent
import com.example.junemon.travelroutine.database.dao.DaoPersonalItems
import com.example.junemon.travelroutine.database.dao.DaoPersonalRoutines
import com.example.junemon.travelroutine.database.dao.DaoPersonalTags
import com.example.junemon.travelroutine.database.dao.newsdao.DaoPersonalNews
import com.example.junemon.travelroutine.database.dao.newsdao.DaoPersonalNewsBussines
import com.example.junemon.travelroutine.database.dao.newsdao.DaoPersonalNewsEntertainment
import com.example.junemon.travelroutine.database.model.PersonalEvent
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalRoutines
import com.example.junemon.travelroutine.database.model.PersonalTags
import com.example.junemon.travelroutine.network.model.PersonalNews
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment

@Database(entities = arrayOf(PersonalItems::class, PersonalRoutines::class, PersonalNews.Article::class,
        PersonalNewsBussines.Article::class, PersonalNewsEntertainment.Article::class, PersonalTags::class,
        PersonalEvent::class), version = 1, exportSchema = false)
@TypeConverters(DateConverts::class)
abstract class MainDatabase : RoomDatabase() {
    companion object {
        private var instances: MainDatabase? = null

        fun getInstances(context: Context): MainDatabase? {
            if (instances == null) {
                synchronized(MainDatabase::class) {
                    instances = Room.databaseBuilder(context.applicationContext, MainDatabase::class.java,
                            "TravelRoutines").build()
                }
            }
            return instances
        }
    }

    abstract fun personalItem_dao(): DaoPersonalItems
    abstract fun personalRoutine_dao(): DaoPersonalRoutines
    abstract fun personalEvent_dao(): DaoPersonalEvent
    abstract fun personalNews_dao(): DaoPersonalNews
    abstract fun personalBussinessNews_dao(): DaoPersonalNewsBussines
    abstract fun personalEntertainmentNews_dao(): DaoPersonalNewsEntertainment
    abstract fun personalTags_dao(): DaoPersonalTags
}