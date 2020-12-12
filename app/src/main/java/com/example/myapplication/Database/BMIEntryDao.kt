package com.example.myapplication.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BMIEntryDao {

    @Query("Select * from Entry")
    fun getAll(): Flow<List<BMIEntry>>

    @Insert
    suspend fun insert(entry: BMIEntry)

    @Delete
    suspend fun delete(entry: BMIEntry)
}