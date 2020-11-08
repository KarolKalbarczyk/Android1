package com.example.myapplication

object BMIDecorator {
    private fun <T> categorize(bmi: Double, under: T, normal: T, over: T, obese: T) = when(bmi){
        in Double.MIN_VALUE..0.0 -> throw IllegalArgumentException("BMI must be a positive value")
        in 0.0..18.5 -> under
        in 18.5..25.0 -> normal
        in 25.0..30.0 -> over
        else -> obese
    }

    fun getColor(bmi: Double) = categorize(bmi,
        R.color.underweight,
        R.color.normal,
        R.color.overweight,
        R.color.obese)

    fun getDescription(bmi: Double) = categorize(bmi,
        R.string.underweight,
        R.string.normal,
        R.string.overweight,
        R.string.obese)
}