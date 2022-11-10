package com.example.gukrule

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gukrule.adapter.VisualBudgetRVAdapter
import com.example.gukrule.data.BudgetData
import com.example.gukrule.databinding.ActivityVisualBinding
import com.example.gukrule.retrofit.*
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse
import kotlin.coroutines.CoroutineContext


class VisualActivity : AppCompatActivity(), CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private lateinit var binding: ActivityVisualBinding

    // 2019,2020,2021,2022 데이터
    private var annual2019BudgetData = ArrayList<Row>()
    private var annual2020BudgetData = ArrayList<Row>()
    private var annual2021BudgetData = ArrayList<Row>()
    private var annual2022BudgetData = ArrayList<Row>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 단위사업명 string array
        val unitBusinessList = resources.getStringArray(R.array.unit_business)

        // add dummy data
        val dataList = ArrayList<BudgetData>()
        for(i: Int in 1..20) {
            dataList.add(
                BudgetData(
                budget_icon = "test",
                budget_title = unitBusinessList[i],
                program_name = unitBusinessList[i]
            ))
        }

        // setting recycler view
        val visualBudgetRVAdapter = VisualBudgetRVAdapter(dataList)
        val budgetRV = binding.budgetRV
        budgetRV.adapter = visualBudgetRVAdapter
        binding.budgetRV.layoutManager= LinearLayoutManager(this)

        // recyclerView Adapter 설정
        visualBudgetRVAdapter.setItemClickListener(object : VisualBudgetRVAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {

                // 년도별 데이터 리스트 초기화
                annual2019BudgetData.clear()
                annual2020BudgetData.clear()
                annual2021BudgetData.clear()
                annual2022BudgetData.clear()

                launch {
                    try {
                        annual2019BudgetData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2019", actvName = "입법정보지원").await().njzofberazvhjncha[1].row
                        annual2020BudgetData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2020", actvName = "입법정보지원").await().njzofberazvhjncha[1].row
                        annual2021BudgetData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2021", actvName = "입법정보지원").await().njzofberazvhjncha[1].row
                        annual2022BudgetData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2022", actvName = "입법정보지원").await().njzofberazvhjncha[1].row

                    } catch (e: Exception) {
                        print("error : ${e.message}")
                    }

                    calculateBudgetBarData()
                }

                binding.visualPanel.panelState = PanelState.COLLAPSED
                binding.chartScroll.fullScroll(ScrollView.FOCUS_UP)
            }
        })



        // floating action button 설정
        binding.fab.setOnClickListener {
            binding.visualPanel.panelState = PanelState.EXPANDED
        }

        initUI()
        initBarChart(binding.anexpBdgamtBarGraph)
        initBarChart(binding.anexpBdgCamtBarGraph)
        initBarChart(binding.epAmtBarGraph)
        initPieChart()
        initBubbleChart()
    }

    private fun initUI() {
        // slidingUpPanel 설정
        binding.visualPanel.isTouchEnabled = false
        binding.visualPanel.addPanelSlideListener(PanelEventListener())

        // actionBar 설정
        setSupportActionBar(binding.budgetToolbar)
        supportActionBar?.apply {
            setDisplayUseLogoEnabled(true)
            title = "시각화"
        }

    }

    fun calculateBudgetBarData() {
        val annualBudgetGraphEntry = ArrayList<BarEntry>()          // 세출예산액 Data Entry
        val annualCurrentBudgetGraphEntry = ArrayList<BarEntry>()   // 세출예산현액 Data Entry
        val expenseBudgetGraphEntry = ArrayList<BarEntry>()         // 지출금액 Data Entry

        // 각 예산액 ArrayList 에 년도별 Data 할당
        annualBudgetGraphEntry.add(BarEntry((2019).toFloat(), (annual2019BudgetData.sumOf { it.ANEXP_BDGAMT } / 1000000).toFloat()))
        annualBudgetGraphEntry.add(BarEntry((2020).toFloat(), (annual2020BudgetData.sumOf { it.ANEXP_BDGAMT } / 1000000).toFloat()))
        annualBudgetGraphEntry.add(BarEntry((2021).toFloat(), (annual2021BudgetData.sumOf { it.ANEXP_BDGAMT } / 1000000).toFloat()))
        annualBudgetGraphEntry.add(BarEntry((2022).toFloat(), (annual2022BudgetData.sumOf { it.ANEXP_BDGAMT } / 1000000).toFloat()))

        annualCurrentBudgetGraphEntry.add(BarEntry((2019).toFloat(), (annual2019BudgetData.sumOf { it.ANEXP_BDG_CAMT } / 1000000).toFloat()))
        annualCurrentBudgetGraphEntry.add(BarEntry((2020).toFloat(), (annual2020BudgetData.sumOf { it.ANEXP_BDG_CAMT } / 1000000).toFloat()))
        annualCurrentBudgetGraphEntry.add(BarEntry((2021).toFloat(), (annual2021BudgetData.sumOf { it.ANEXP_BDG_CAMT } / 1000000).toFloat()))
        annualCurrentBudgetGraphEntry.add(BarEntry((2022).toFloat(), (annual2022BudgetData.sumOf { it.ANEXP_BDG_CAMT } / 1000000).toFloat()))

        expenseBudgetGraphEntry.add(BarEntry((2019).toFloat(), (annual2019BudgetData.sumOf { it.EP_AMT } / 1000000).toFloat()))
        expenseBudgetGraphEntry.add(BarEntry((2020).toFloat(), (annual2020BudgetData.sumOf { it.EP_AMT } / 1000000).toFloat()))
        expenseBudgetGraphEntry.add(BarEntry((2021).toFloat(), (annual2021BudgetData.sumOf { it.EP_AMT } / 1000000).toFloat()))
        expenseBudgetGraphEntry.add(BarEntry((2022).toFloat(), (annual2022BudgetData.sumOf { it.EP_AMT } / 1000000).toFloat()))

        // 각 예산액 그래프 데이터 할당
        setBarChartData(binding.anexpBdgamtBarGraph, annualBudgetGraphEntry)
        setBarChartData(binding.anexpBdgCamtBarGraph, annualCurrentBudgetGraphEntry)
        setBarChartData(binding.epAmtBarGraph, expenseBudgetGraphEntry)




    }

    private fun initBarChart(barGraph : BarChart) {
        // 바텀 좌표 값
        val xAxis: XAxis = barGraph.xAxis
        // x축 위치 설정
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        // 그리드 선 수평 거리 설정
        xAxis.granularity = 1f
        // x축 텍스트 컬러 설정
        xAxis.textColor = ContextCompat.getColor(applicationContext, R.color.navy)
        // x축 선 설정 (default = true)
        xAxis.setDrawAxisLine(false)
        // 격자선 설정 (default = true)
        xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = barGraph.axisLeft
//        // 좌측 선 설정 (default = true)
        leftAxis.setDrawAxisLine(false)
//        // 좌측 텍스트 컬러 설정
//        leftAxis.textColor = ContextCompat.getColor(applicationContext, R.color.navy)

        val rightAxis: YAxis = barGraph.axisRight
        // 우측 선 설정 (default = true)
        rightAxis.setDrawAxisLine(false)
        // 우측 텍스트 컬러 설정
        rightAxis.textColor = ContextCompat.getColor(applicationContext, R.color.navy)

        // 바차트의 타이틀
        val legend: Legend = barGraph.legend
        // 범례 모양 설정 (default = 정사각형)
        legend.form = Legend.LegendForm.LINE
        // 타이틀 텍스트 사이즈 설정
        legend.textSize = 20f
        // 타이틀 텍스트 컬러 설정
        legend.textColor = Color.BLACK
        // 범례 위치 설정
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        // 범례 방향 설정
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
    }

    private fun initPieChart() {
        binding.detailedBudgetPieGraph.setUsePercentValues(true)
        binding.detailedBudgetPieGraph.description.isEnabled = false
        binding.detailedBudgetPieGraph.setExtraOffsets(5F, 10F, 5F, 5F)

        binding.detailedBudgetPieGraph.dragDecelerationFrictionCoef = 0.95f

        binding.detailedBudgetPieGraph.isDrawHoleEnabled = false
        binding.detailedBudgetPieGraph.setHoleColor(Color.WHITE)
        binding.detailedBudgetPieGraph.transparentCircleRadius = 61f

        val yValues = ArrayList<PieEntry>()

        yValues.add(PieEntry(34f, "단위사업1"))
        yValues.add(PieEntry(23f, "단위사업2"))
        yValues.add(PieEntry(14f, "단위사업3"))
        yValues.add(PieEntry(35f, "단위사업4"))
        yValues.add(PieEntry(40f, "단위사업5"))

        val description = Description()
        description.text = "세계 국가" //라벨

        description.textSize = 15f
        binding.detailedBudgetPieGraph.description = description

        val dataSet = PieDataSet(yValues, "Countries")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.JOYFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.YELLOW)

        binding.detailedBudgetPieGraph.data = data
        binding.detailedBudgetPieGraph.animateY(1000, Easing.EaseInOutCubic) //애니메이션

    }

    private fun initBubbleChart() {
        val bubbleDataList = getEntries()
        val bubbleDataSet = BubbleDataSet(bubbleDataList, "")
        val bubbleData = BubbleData(bubbleDataSet)
        binding.customBubbleChart.data = bubbleData
        binding.customBubbleChart.setScaleEnabled(false)

        bubbleDataSet.colors = ColorTemplate.JOYFUL_COLORS.toList()
        bubbleDataSet.valueTextColor = Color.BLACK
        bubbleDataSet.valueTextSize = 18f
    }

    private fun getEntries() : ArrayList<BubbleEntry> {
        val bubbleEntries = ArrayList<BubbleEntry>()
        bubbleEntries.add(BubbleEntry(0F, 1F, 0.21f))
        bubbleEntries.add(BubbleEntry(1F, 2F, 0.12f))
        bubbleEntries.add(BubbleEntry(2F, 3F, 0.20f))
        bubbleEntries.add(BubbleEntry(2F, 4F, 0.52f))
        bubbleEntries.add(BubbleEntry(3F, 5F, 0.29f))
        bubbleEntries.add(BubbleEntry(4F, 6F, 0.62f))
        return bubbleEntries
    }

    private fun setBarChartData(barGraph : BarChart, budgetDataEntry: ArrayList<BarEntry>) {
        // Zoom In / Out 가능 여부 설정
        barGraph.setScaleEnabled(false)

        val barDataSet = BarDataSet(budgetDataEntry, "")
        barGraph.description.isEnabled = false

        // 바 색상 설정 (ColorTemplate.LIBERTY_COLORS)
        barDataSet.setColors(
            Color.rgb(207, 248, 246), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187),
            Color.rgb(118, 174, 175), Color.rgb(42, 109, 130))

        barGraph.data = BarData(barDataSet)
        // 차트 갱신
        barGraph.invalidate()
        barGraph.animateY(1000)
        barGraph.animateX(1000)
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