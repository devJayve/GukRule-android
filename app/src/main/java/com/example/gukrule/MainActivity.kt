package com.example.gukrule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.gukrule.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("route", ""+binding.root)

        setSupportActionBar(findViewById(R.id.main_toolbar))
        supportActionBar?.apply {
            setIcon(R.drawable.tmp_resize)
            setDisplayUseLogoEnabled(true)
            setTitle(R.string.app_name)
        }



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        Log.d("nav", ""+navController)


    }

    fun moveToAllPost() {
        val intent = Intent(this, NewsActivity::class.java)
        intent.putExtra("isKeywordExist", false)
        startActivity(intent)
    }

    fun moveToArticle(keyword: String, articleUrl: String ,imgUrl: String) {
        Log.d("LOG", "this is - moveToArticle")
        val intent = Intent(this, ArticleVisualActivity::class.java)
        intent.putExtra("keyword", keyword)
        intent.putExtra("articleUrl", articleUrl)
        intent.putExtra("imgUrl", imgUrl)
        startActivity(intent)
    }
}