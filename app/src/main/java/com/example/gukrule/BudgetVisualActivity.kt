package com.example.gukrule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.databinding.ActivityBudgetVisualBinding
import com.example.gukrule.databinding.ActivityMainBinding

class BudgetVisualActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBudgetVisualBinding
    var isUp = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBudgetVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getBudgetType = intent.getIntExtra("budget_type", 0)
        binding.testText.text = getBudgetType.toString()

        binding.testUpButton.setOnClickListener {
            drawUpVisualGraph()
        }
    }

    fun drawUpVisualGraph() {
        val translateUp: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.translate_up)
        val translateDown: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.translate_down)
        if(isUp) {
            binding.bottomUpPage.visibility = View.GONE
            binding.bottomUpPage.startAnimation(translateDown)
        } else {
            binding.bottomUpPage.visibility = View.VISIBLE
            binding.bottomUpPage.startAnimation(translateUp)
        }
        isUp = !isUp
    }
}