package com.example.myapplication.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.Units
import java.util.*

@Entity(tableName = "Entry")
class BMIEntry(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Id") val id: Int,
               @ColumnInfo(name = "Height") val height: Double,
               @ColumnInfo(name = "Mass") val mass: Double,
               @ColumnInfo(name = "Bmi") val bmi: Double,
               @TypeConverters(DateConverter::class) @ColumnInfo(name = "Date")  val date: Date,
               @ColumnInfo(name = "Units") val units: Units
)