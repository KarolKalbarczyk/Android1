package com.example.myapplication.Database

import androidx.annotation.WorkerThread

class BMIEntryRepository(val dao: BMIEntryDao) {
    val allEntries = dao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(bmiEntry: BMIEntry) {
        dao.insert(bmiEntry)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(bmiEntry: BMIEntry){
        dao.delete(bmiEntry)
    }
}