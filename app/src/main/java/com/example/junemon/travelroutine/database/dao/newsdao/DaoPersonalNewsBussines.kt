package com.example.junemon.travelroutine.database.dao.newsdao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.network.model.PersonalNewsBussines
import io.reactivex.Flowable

@Dao
interface DaoPersonalNewsBussines {

    @Query("SELECT * FROM personal_bussines_news")
    fun getAllData(): Flowable<List<PersonalNewsBussines.Article>>

    @Query("SELECT * FROM personal_bussines_news")
    fun getLiveAllData(): LiveData<List<PersonalNewsBussines.Article>>

    @Query("SELECT * FROM personal_bussines_news WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<PersonalNewsBussines.Article>

    @Insert
    fun insertData(insertData: List<PersonalNewsBussines.Article>?)

    @Delete
    fun deleteData(deleteData: PersonalNewsBussines.Article?)

    @Query("DELETE FROM personal_bussines_news")
    fun deleteAllData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateData: PersonalNewsBussines.Article?)
}