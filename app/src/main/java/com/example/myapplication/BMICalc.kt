package com.example.myapplication

interface BMICalc {
    fun calculateBMI(height: Double, mass: Double): Double
}

class MetricBMICalc : BMICalc{
    override fun calculateBMI(height: Double, mass: Double) = mass / (height * height / 10000)
}

class ImperialBMICalc : BMICalc{
    override fun calculateBMI(height: Double, mass: Double) =  mass / (height * height) * 703
}

enum class Units{
    Imperial,
    Metric,
}

fun convert(endUnits: Units, height: Double, mass: Double) = when(endUnits){
    Units.Imperial -> Pair(height * 0.393700787, mass * 2.20462262)
    Units.Metric -> Pair(height * 2.54, mass * 0.45359237)
}