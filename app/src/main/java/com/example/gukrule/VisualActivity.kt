package com.example.gukrule

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gukrule.adapter.CarouselRVAdapter
import com.example.gukrule.adapter.VisualBudgetRVAdapter
import com.example.gukrule.data.BudgetData
import com.example.gukrule.databinding.ActivityVisualBinding
import com.example.gukrule.retrofit.DetailBudgetData
import com.example.gukrule.retrofit.DetailBudgetList
import com.example.gukrule.retrofit.JsonRowList
import com.example.gukrule.retrofit.RetrofitClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import retrofit2.Call
import retrofit2.Response
import retrofit2.create

class VisualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisualBinding
    private var budgetInfoList = ArrayList<DetailBudgetData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.visualPanel.isTouchEnabled = false
        binding.visualPanel.addPanelSlideListener(PanelEventListener())

        setSupportActionBar(binding.budgetToolbar)
        supportActionBar?.apply {
            setIcon(R.drawable.tmp_resize)
            setDisplayUseLogoEnabled(true)
            title = "시각화"
        }

        val dataList = ArrayList<BudgetData>()

        for(i: Int in 1..20) {
            dataList.add(
                BudgetData(
                budget_icon = "test",
                budget_title = "this title index is $i",
                program_name = "$i 번째 프로그램"
            ))
        }

        val visualBudgetRVAdapter = VisualBudgetRVAdapter(dataList)
        val budgetRV = binding.budgetRV
        budgetRV.adapter = visualBudgetRVAdapter
        binding.budgetRV.layoutManager= LinearLayoutManager(this)

        visualBudgetRVAdapter.setItemClickListener(object : VisualBudgetRVAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                budgetInfoList.clear()
                for (i in 0 .. 3) {
                    hostRetrofitClient((2019+i).toString(), "국회도서관")
                }
                binding.visualPanel.panelState = PanelState.COLLAPSED
            }
        })

        // floating action btn
        binding.fab.setOnClickListener {
            binding.visualPanel.panelState = PanelState.EXPANDED
        }



    }

    private fun hostRetrofitClient(fsclYY : String, programName : String) {
        val retrofit = RetrofitClient.initCongressRetrofit()
        Log.d("LOG", "fsclYY : $fsclYY, pgmName : $programName")
        val budgetDetailApi = retrofit.create(RetrofitClient.BudgetApi::class.java)

        budgetDetailApi.getDetailBusiness(fsclYY = fsclYY, pgmName = programName)
            .enqueue(object : retrofit2.Callback<DetailBudgetList> {
            override fun onResponse(
                call: Call<DetailBudgetList>,
                response: Response<DetailBudgetList>
            ) {
                val jsonResponse: JsonObject = response.body()!!.njzofberazvhjncha[1]
                val jsonRowList = Gson().fromJson(jsonResponse, JsonRowList::class.java)
                val finalData = Gson().fromJson(jsonRowList.row[0], DetailBudgetData::class.java)
                budgetInfoList.add(finalData)
                Log.d("success", budgetInfoList.count().toString())
            }

            override fun onFailure(call: Call<DetailBudgetList>, t: Throwable) {
                Log.d("failure", t.message.toString())
            }
        })
    }


    // 이벤트 리스너
    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        // 패널이 슬라이드 중일 때
        override fun onPanelSlide(panel: View?, slideOffset: Float) {

        }

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(panel: View?, previousState: PanelState?, newState: PanelState?) {
            if (newState == PanelState.COLLAPSED) {
//                binding.programListTitle.text = "다른거 보려면 클릭하세요"
//                binding.programListTitle.gravity = Gravity.CENTER
            } else if (newState == PanelState.EXPANDED) {
//                binding.programListTitle.text = "국회 프로그램 목록"
//                binding.programListTitle.gravity = Gravity.START
            }
        }
    }
}