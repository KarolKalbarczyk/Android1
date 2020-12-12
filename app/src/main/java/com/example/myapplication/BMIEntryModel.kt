package com.example.myapplication

import java.util.*

data class BMIEntryModel(val heightText: String,
                    val massText: String,
                    val height: Double,
                    val mass: Double,
                    val bmi: Double,
                    val date: Date
){
    override fun toString() =
        """
            ${massText} ${mass}
            ${heightText} ${height}
            bmi: ${bmi}
            ${date.toLocaleString()}
        """.trimIndent()
}

