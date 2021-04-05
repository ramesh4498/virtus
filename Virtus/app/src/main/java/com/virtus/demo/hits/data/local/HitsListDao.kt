package com.virtus.demo.hits.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.virtus.demo.hits.data.entities.Hits

@Dao
interface HitsListDao {

    @Query("SELECT * FROM hits ORDER BY created_at DESC")
    fun getAllHits() : LiveData<List<Hits>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hits: List<Hits>)

    @Delete
    fun delete(hits: Hits)

    @Query("DELETE FROM hits")
    fun  deleteAll();
}