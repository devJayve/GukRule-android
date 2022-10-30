package com.example.gukrule


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import com.example.gukrule.adapter.KeywordRVAdapter
import com.example.gukrule.databinding.ActivityKeywordVisualBinding



class KeywordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKeywordVisualBinding
    private lateinit var keywordAdapter: KeywordRVAdapter
    private lateinit var gridManger : GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityKeywordVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        keywordAdapter = KeywordRVAdapter()

        gridManger = GridLayoutManager(this, 3)
        gridManger.orientation = GridLayoutManager.VERTICAL

        binding.keywordRecyclerGrid.apply{
            layoutManager=gridManger
            adapter = keywordAdapter
        }


    }

}