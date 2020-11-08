package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityBMIInfoBinding


class BMIInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBMIInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBMIInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bmi = intent.getDoubleExtra(BMIKEY, 0.0)

        binding.apply {
            bmiValueTV.text = bmi.toStringFormat()
            bmiTextTV.text = getString(BMIDecorator.getDescription(bmi))
        }
    }

    fun goBack(view: View){
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

}