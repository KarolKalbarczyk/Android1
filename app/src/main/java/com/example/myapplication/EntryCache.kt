package com.example.myapplication

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import org.json.JSONException
import java.lang.Exception
import java.util.*


object EntryCache {
    private val numberOfEntries: Int = 10
    private val KEY = "KEY"

    private fun getEntriesPriv(sharedPreferences: SharedPreferences): MutableList<BMIEntry> =
        try { Json.decodeFromString(serializer(),sharedPreferences.getString(KEY, "") ?: "") }
        catch (e: SerializationException) { mutableListOf()}

    fun getEntries(sharedPreferences: SharedPreferences) : List<BMIEntry> = getEntriesPriv(sharedPreferences)

    fun addEntry(entry: BMIEntry, sharedPreferences: SharedPreferences): Unit {
        val list = getEntriesPriv(sharedPreferences)
        if (list.size == numberOfEntries) list.removeAt(0)
        list.add(entry)
        sharedPreferences.edit {  putString(KEY, Json.encodeToString(list)) }
    }
}

@Serializable
data class BMIEntry(val heightText: String,
                    val massText: String,
                    val height: Double,
                    val mass: Double,
                    val bmi: Double,
                    @Serializable(DateSerializer::class)
                    val date: Date = Date()
){
    override fun toString() =
        """
            ${massText} ${mass}
            ${heightText} ${height}
            bmi: ${bmi}
            ${date.toLocaleString()}
        """.trimIndent()
}

@Serializer(forClass = Date::class)
object DateSerializer : KSerializer<Date> {

    override fun deserialize(decoder: Decoder): Date {
        val date = Date()
        date.time = Date.parse(decoder.decodeString())
        return date
    }

    override fun serialize(encoder: Encoder, value: Date) : Unit {
        encoder.encodeString(value.toString())
    }

}
