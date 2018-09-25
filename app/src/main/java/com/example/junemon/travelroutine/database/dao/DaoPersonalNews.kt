package com.example.junemon.travelroutine.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.network.model.PersonalNews
import io.reactivex.Flowable

@Dao
interface DaoPersonalNews {

    @Query("SELECT * FROM personal_news")
    fun getAllData(): Flowable<List<PersonalNews.Article>>

    @Query("SELECT * FROM personal_news")
    fun getLiveAllData(): LiveData<List<PersonalNews.Article>>

    @Query("SELECT * FROM personal_belongings WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<PersonalNews.Article>

    @Insert
    fun insertData(insertData: List<PersonalNews.Article>?)

    @Delete
    fun deleteData(deleteData: PersonalNews.Article?)

    @Query("DELETE FROM personal_news")
    fun deleteAllData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateData: PersonalNews.Article?)
}