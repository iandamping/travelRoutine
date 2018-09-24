package com.example.junemon.travelroutine.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.database.model.PersonalItems
import io.reactivex.Flowable

@Dao
interface DaoPersonalItems {

    @Query("SELECT * FROM personal_belongings")
    fun getAllData(): Flowable<List<PersonalItems>>

    @Query("SELECT * FROM personal_belongings")
    fun getLiveAllData(): LiveData<List<PersonalItems>>

    @Query("SELECT * FROM personal_belongings WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<PersonalItems>

    @Insert
    fun insertData(insertData: PersonalItems?)

    @Delete
    fun deleteData(deleteData: PersonalItems?)

    @Query("DELETE FROM personal_belongings")
    fun deleteAllData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateData: PersonalItems?)
}