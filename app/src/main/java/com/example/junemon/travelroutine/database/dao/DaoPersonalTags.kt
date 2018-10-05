package com.example.junemon.travelroutine.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.junemon.travelroutine.database.model.PersonalTags

@Dao
interface DaoPersonalTags {

    @Query("SELECT * from personal_tags")
    fun getAllData(): LiveData<List<PersonalTags>>

    @Insert
    fun insertData(newTags: PersonalTags)

    @Query("SELECT * FROM personal_tags WHERE ID = :id")
    fun getLiveDataById(id: Int?): LiveData<PersonalTags>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateData(updateTags: PersonalTags)

    @Delete
    fun deleteData(deleteTags: PersonalTags)

    @Query("DELETE FROM personal_tags")
    fun deleteAllData()
}