package com.example.gukrule

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gukrule.adapter.SelectRVAdapter
import com.example.gukrule.databinding.ActivitySelectBinding

class SelectActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySelectBinding
    private lateinit var selectAdapter: SelectRVAdapter
    private lateinit var gridManger : GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectBinding.inflate(layoutInflater)

        setContentView(binding.root)
        selectAdapter = SelectRVAdapter()

        gridManger = GridLayoutManager(this,2)
        gridManger.orientation = GridLayoutManager.VERTICAL

        binding.selectArticleGrid.apply{
            layoutManager=gridManger
            adapter = selectAdapter

        }
    }
}