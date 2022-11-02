package com.example.gukrule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.databinding.ActivityBudgetVisualBinding
import com.example.gukrule.databinding.ActivityMainBinding

class BudgetVisualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBudgetVisualBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBudgetVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getBudgetType = intent.getIntExtra("budget_type", 0)
        binding.testText.text = getBudgetType.toString()
    }
}