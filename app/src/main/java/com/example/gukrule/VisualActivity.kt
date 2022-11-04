package com.example.gukrule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState

class VisualActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisualBinding

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

        var dataList = ArrayList<BudgetData>()

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
                binding.visualPanel.panelState = PanelState.COLLAPSED
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
                binding.budgetRV.visibility = View.GONE
                binding.visualPanel.isTouchEnabled = true
//                binding.programListTitle.text = "다른거 보려면 클릭하세요"
//                binding.programListTitle.gravity = Gravity.CENTER
            } else if (newState == PanelState.EXPANDED) {
                binding.budgetRV.visibility = View.VISIBLE
                binding.visualPanel.isTouchEnabled = false
//                binding.programListTitle.text = "국회 프로그램 목록"
//                binding.programListTitle.gravity = Gravity.START
            }
        }
    }
}