package com.example.myapplication

import android.content.res.Resources
import androidx.lifecycle.*
import com.example.myapplication.Database.BMIEntry
import com.example.myapplication.Database.BMIEntryRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*


class EntryCacheViewModel(private val repository: BMIEntryRepository, private val res: Resources) : ViewModel() {

    private val numberOfEntries: Int = 10

    private val entries = repository.allEntries.asLiveData()

    val allEntries: LiveData<List<BMIEntryModel>> = Transformations.map(entries) {
            list -> list.map { entityToModel(it) }
    }

    private fun entityToModel(entity: BMIEntry) =
        BMIEntryModel(
            heightText = res.getString(R.string.height)
                .interpolate(
                if (entity.units == Units.Metric)
                    res.getString(R.string.cm)
                else res.getString(R.string.`in`)
                ),
            massText = res.getString(R.string.mass)
                .interpolate(
                    if (entity.units == Units.Metric)
                        res.getString(R.string.kg)
                    else res.getString(R.string.pounds)
                ),
            height = entity.height,
            mass = entity.mass,
            bmi = entity.bmi,
            date = entity.date
        )

    suspend fun addEntry(units: Units,
                 height: Double,
                 mass: Double,
                 bmi: Double,
                 date: Date = Date()
    ): Unit {
        val list = repository.allEntries.first()
        if (list.size == numberOfEntries)
            viewModelScope.launch {  repository.delete(list[0]) }
        val newEntity = BMIEntry(0, height, mass, bmi, date, units)
        viewModelScope.launch { repository.insert(newEntity) }
    }
}

class WordViewModelFactory(private val repository: BMIEntryRepository, private val res: Resources) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EntryCacheViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EntryCacheViewModel(repository, res) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}