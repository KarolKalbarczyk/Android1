package com.example.myapplication

import android.text.Editable

fun Editable.getDouble() = this.toString().toDoubleOrNull()

fun String.interpolate(vararg values: Any): String{
    var s = this
    for(value in values){
        if (!s.contains("{}"))
            throw IllegalArgumentException("Wrong number of arguments")
        s = s.replaceFirst("{}", value.toString())
    }
    return s
}

fun Double.toStringFormat() =
    this.toString().replace(Regex("""(?<=\...).*"""), "") //nie chce uzyc String.format bo to zamienia kopki na przecinki i inne tego typu rzeczy
