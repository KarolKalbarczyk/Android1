package com.example.myapplication

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.shouldBe
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ConversionTest: StringSpec({
    val height = 1.0
    val mass = 1.0
    val (impHeight, impMass) = convert(Units.Imperial, height, mass)
    "conversion from metric to imperial should be ok" {
        impHeight shouldBe (0.3937 plusOrMinus 0.01)
        impMass shouldBe (2.2046 plusOrMinus 0.01)
    }
    "conversion from imperial to metric should be ok" {
        val (mHeight, mMass) = convert(Units.Metric, impHeight, impMass)
        mHeight shouldBe (height plusOrMinus 0.01)
        mMass shouldBe (mass plusOrMinus 0.01)
    }
})

class BMIMetricCalcTest: StringSpec({
    val calc = MetricBMICalc()
    val height = 180.0
    val mass = 70.0
    "bmi should be 21.6 in metric calc" {
        calc.calculateBMI(height, mass) shouldBe (21.6 plusOrMinus 0.01)
    }
})


class Imperial: StringSpec({
    val calc = ImperialBMICalc()
    val height = 72.0
    val mass = 180.0
    "bmi should be 24.4 in imperial calc" {
        calc.calculateBMI(height, mass) shouldBe (24.4 plusOrMinus 0.01)
    }
})

