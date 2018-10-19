package com.example.junemon.travelroutine.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.database.model.PersonalEvent

@Dao
interface DaoPersonalEvent {
    @Query("SELECT * FROM personal_event")
    fun getLiveAllData(): LiveData<List<PersonalEvent>>

    @Query("SELECT * FROM personal_event WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<PersonalEvent>

    @Insert
    fun insertData(insertData: PersonalEvent?)

    @Delete
    fun deleteData(deleteData: PersonalEvent?)

    @Query("DELETE FROM personal_event")
    fun deleteAllData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateData: PersonalEvent?)
}