package com.example.gukrule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gukrule.databinding.ActivityVisualBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class VisualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}