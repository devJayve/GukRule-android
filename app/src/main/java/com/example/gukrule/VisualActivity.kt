package com.example.gukrule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gukrule.databinding.AcitivityVisualBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class VisualActivity : AppCompatActivity() {
    private lateinit var binding: AcitivityVisualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AcitivityVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}