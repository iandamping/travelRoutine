package com.example.junemon.travelroutine.database.dao.newsdao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.network.model.PersonalNewsEntertainment
import io.reactivex.Flowable

@Dao
interface DaoPersonalNewsEntertainment {

    @Query("SELECT * FROM personal_bussines_entertainment")
    fun getAllData(): Flowable<List<PersonalNewsEntertainment.Article>>

    @Query("SELECT * FROM personal_bussines_entertainment")
    fun getLiveAllData(): LiveData<List<PersonalNewsEntertainment.Article>>

    @Query("SELECT * FROM personal_bussines_entertainment WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<PersonalNewsEntertainment.Article>

    @Insert
    fun insertData(insertData: List<PersonalNewsEntertainment.Article>?)

    @Delete
    fun deleteData(deleteData: PersonalNewsEntertainment.Article?)

    @Query("DELETE FROM personal_bussines_entertainment")
    fun deleteAllData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateData: PersonalNewsEntertainment.Article?)
}