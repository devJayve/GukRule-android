package com.example.gukrule


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gukrule.retrofit.adapter.KeywordRVAdapter
import com.example.gukrule.databinding.ActivityKeywordVisualBinding

class KeywordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKeywordVisualBinding
    private lateinit var keywordAdapter: KeywordRVAdapter
    private lateinit var gridManger : GridLayoutManager
    private var doubleClicked = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityKeywordVisualBinding.inflate(layoutInflater)

//        결과 받아서 다시 메인 액티비티로 화면 되돌리는 경우 - 메인액티비티에 쓸 코드
//
//        private val registerActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult() // ◀ StartActivityForResult 처리를 담당
//        ) { result ->
//            if (result.resultCode == RESULT_OK) {
//                val value = result.data?.getStringExtra("resultData")
//                ... 생략 ...
//            }
//        }


        setContentView(binding.root)
        returnData()
        keywordAdapter = KeywordRVAdapter()

        gridManger = GridLayoutManager(this, 3)
        gridManger.orientation = GridLayoutManager.VERTICAL

        binding.keywordRecyclerGrid.apply{
            layoutManager=gridManger
            adapter = keywordAdapter
        }

    }

    private fun returnData(){

        val keywordRVAdapter = KeywordRVAdapter()

        keywordRVAdapter.setItemClickListener(object : KeywordRVAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                intent.putExtra("selectedKeyword",position)
                setResult(RESULT_OK,intent)
                finish()
            }
        })
    }

    override fun onBackPressed() {
        if (doubleClicked == true){
            finish()
        }
        doubleClicked = true

        Handler().postDelayed(Runnable {
            doubleClicked = false
        },1000)
    }


}