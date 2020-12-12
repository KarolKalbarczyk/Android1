package com.example.myapplication

import android.app.Application
import com.example.myapplication.Database.AppDatabase
import com.example.myapplication.Database.BMIEntryRepository

class BMIApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BMIEntryRepository(database.bmiEntryDao()) }
}