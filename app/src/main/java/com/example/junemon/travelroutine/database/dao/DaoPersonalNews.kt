package com.example.junemon.travelroutine.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.network.model.NewsAll
import io.reactivex.Flowable

@Dao
interface DaoPersonalNews {

    @Query("SELECT * FROM personal_belongings")
    fun getAllData(): Flowable<List<NewsAll.Article>>

    @Query("SELECT * FROM personal_belongings")
    fun getLiveAllData(): LiveData<List<NewsAll.Article>>

    @Query("SELECT * FROM personal_belongings WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<NewsAll.Article>

    @Insert
    fun insertData(insertData: NewsAll.Article?)

    @Delete
    fun deleteData(deleteData: NewsAll.Article?)

    @Query("DELETE FROM personal_belongings")
    fun deleteAllData()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateData: NewsAll.Article?)
}