package com.vorobyoff.weather.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vorobyoff.weather.databinding.ActivityAppBinding
import com.vorobyoff.weather.databinding.ActivityAppBinding.inflate

class AppActivity : AppCompatActivity() {
    private val binding: ActivityAppBinding by lazy { inflate(layoutInflater) }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(binding.root)
    }
}