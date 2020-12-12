package com.example.myapplication.Database

import android.content.Context
import androidx.room.*
import com.example.myapplication.Units
import java.util.Date

@Database(entities = arrayOf(BMIEntry::class), version = 1)
@TypeConverters(DateConverter::class, UnitsConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bmiEntryDao(): BMIEntryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bmi_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class DateConverter{
    @TypeConverter
    fun fromLong(value: Long?) : Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?) : Long? = date?.time

}

class UnitsConverter {

    @TypeConverter
    fun fromLong(value: Long?) = when(value){
        null -> null
        1L -> Units.Metric
        else -> Units.Imperial
    }

    @TypeConverter
    fun toLong(units: Units?) = when(units){
        null -> null
        Units.Metric  -> 1L
        Units.Imperial -> 2
    }
}