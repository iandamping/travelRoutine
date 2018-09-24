package com.example.junemon.travelroutine.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.database.model.PersonalRoutines

@Dao
interface DaoPersonalRoutines {

    @Query("SELECT * FROM personal_routines")
    fun getLiveAllData(): LiveData<List<PersonalRoutines>>

    @Query("SELECT * FROM personal_routines WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<PersonalRoutines>

    @Insert
    fun insertData(insertData: PersonalRoutines?)

    @Delete
    fun deleteData(deleteData: PersonalRoutines?)

    @Query("DELETE FROM personal_belongings")
    fun deleteAllData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateData: PersonalRoutines?)
}