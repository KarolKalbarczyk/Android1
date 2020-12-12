package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityMainBinding.inflate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

const val BMIKEY = "bmi"
class MainActivity : AppCompatActivity() {

    private val imperialCalc = ImperialBMICalc()
    private val metricCalc = MetricBMICalc()
    private var bmiCalc : BMICalc = metricCalc
    private val SAVE_MASS = "mass"
    private val SAVE_HEIGHT = "height"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: EntryCacheViewModel by viewModels {
        WordViewModelFactory((application as BMIApplication).repository, resources)
    }

    private fun getMaxMass() = if(bmiCalc is MetricBMICalc) 300.0 else 300 * 2.20462262
    private fun getMaxHeight() = if(bmiCalc is MetricBMICalc) 240.0 else 240 * 0.45359237


    private fun ActivityMainBinding.getData() = Pair(heightET.text.getDouble(), massET.text.getDouble())

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_units) changeUnits()
        if (item.itemId == R.id.show_history) openHistory()

        return true
    }

    private fun changeUnits(): Unit {
        binding.apply {
            fun convertET(units: Units): Unit {
                val (height, mass) = getData()
                if (height == null || mass == null) return

                val (newHeight, newMass) = convert(units, height, mass)
                heightET.setText(newHeight.toStringFormat())
                massET.setText(newMass.toStringFormat())
            }

            val isMetric = bmiCalc is MetricBMICalc
            convertET(if (bmiCalc is MetricBMICalc) Units.Imperial else Units.Metric)
            bmiCalc = if (isMetric) imperialCalc else metricCalc
            setTV()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        setTV()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        binding.apply {
            outState.putString(SAVE_HEIGHT, heightET.text.toString())
            outState.putString(SAVE_MASS, massET.text.toString())
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.apply {
            heightET.setText(savedInstanceState.getString(SAVE_HEIGHT))
            massET.setText(savedInstanceState.getString(SAVE_MASS))
        }
    }

    private fun getValuesWithUnits(): Pair<String, String> {
        val (massUnit, heightUnit) =
            if (bmiCalc is MetricBMICalc) Pair(getString(R.string.kg), getString(R.string.cm))
            else Pair(getString(R.string.pounds), getString(R.string.`in`))

        return Pair(getString(R.string.height).interpolate(heightUnit), getString(R.string.mass).interpolate(massUnit))
    }
    private fun setTV(): Unit{
        val (height, mass) =
            getValuesWithUnits()

        binding.apply {
            heightTV.text = height
            massTV.text = mass
        }
    }

    fun count(view: View) {
        binding.apply {
            var isError = false

            fun getError(id : Int): String {
                isError = true
                return getString(id)
            }

            val (height, mass) = getData()

            if (heightET.text.isBlank()) heightET.error = getError(R.string.height_is_empty)
            else if (height == null) heightET.error = getError(R.string.not_in_range).interpolate(1.0, getMaxHeight())
            else if (height !in 1.0..getMaxHeight()) heightET.error = getError(R.string.not_in_range).interpolate(1.0, getMaxHeight())

            if (massET.text.isBlank()) massET.error = getError(R.string.mass_is_empty)
            else if (mass == null) massET.error = getError(R.string.not_in_range).interpolate(0.0, getMaxMass())
            else if (mass !in 0.0..getMaxMass()) massET.error = getError(R.string.not_in_range).interpolate(0.0, getMaxMass())

            if (isError) return

            val bmi = bmiCalc.calculateBMI(height!!, mass!!)
            addToCache(bmi, height, mass)
            bmiTV.text = bmi.toStringFormat()
            bmiTV.setTextColor(resources.getColor(BMIDecorator.getColor(bmi), resources.newTheme()))
        }
    }

    private fun addToCache(bmi : Double, height: Double, mass: Double) : Unit {
        val units = if (bmiCalc is MetricBMICalc) Units.Metric  else Units.Imperial
        GlobalScope.launch {  viewModel.addEntry(units,  height, mass, bmi) }
    }

    fun openInfo(view: View) {
        val i = Intent(this, BMIInfoActivity::class.java)
        i.putExtra(BMIKEY, binding.bmiTV.text.toString().toDouble())
        startActivityForResult(i, 1)
    }

    fun openHistory() {
        val i = Intent(this, HistoryActivity::class.java)
        startActivityForResult(i, 11)
    }
}
