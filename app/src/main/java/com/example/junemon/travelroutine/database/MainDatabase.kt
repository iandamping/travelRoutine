package com.example.junemon.travelroutine.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.junemon.travelroutine.database.converter.DateConverts
import com.example.junemon.travelroutine.database.dao.DaoPersonalItems
import com.example.junemon.travelroutine.database.dao.DaoPersonalRoutines
import com.example.junemon.travelroutine.database.model.PersonalItems
import com.example.junemon.travelroutine.database.model.PersonalRoutines

@Database(entities = arrayOf(PersonalItems::class, PersonalRoutines::class), version = 1, exportSchema = false)
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
}