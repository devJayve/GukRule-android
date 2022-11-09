package com.example.gukrule

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gukrule.adapter.VisualBudgetRVAdapter
import com.example.gukrule.data.BudgetData
import com.example.gukrule.databinding.ActivityVisualBinding
import com.example.gukrule.retrofit.*
import com.github.mikephil.charting.animation.Easing
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
    private var detailBudgetDataList = ArrayList<DetailBudgetData>()

    private var annualBudgetList = arrayOfNulls<ArrayList<Row>>(4)

    // 2019 데이터
    private lateinit var annual2019BudgetData : List<Row>
    // 2020 데이터
    private var annual2020BudgetData = ArrayList<Row>()
    // 2021 데이터
    private var annual2021BudgetData = ArrayList<Row>()
    // 2022 데이터
    private var annual2022BudgetData = ArrayList<Row>()



    private val graphValueList = ArrayList<BarEntry>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 단위사업명 string array
        val unitBusinessList = resources.getStringArray(R.array.unit_business)

        initUI()

        // add dummy data
        val dataList = ArrayList<BudgetData>()
        for(i: Int in 1..20) {
            dataList.add(
                BudgetData(
                budget_icon = "test",
                budget_title = "this title index is $i",
                program_name = "$i 번째 프로그램"
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
                detailBudgetDataList.clear()

                launch {
                    val testData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2019", actvName = "입법정보지원").await()
                    Log.d("Success", "2019 ${testData.njzofberazvhjncha[1].row[0].ANEXP_BDGAMT}")
                }

                launch {
                    val testData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2020", actvName = "입법정보지원").await()
                    Log.d("Success", "2020 ${testData.njzofberazvhjncha[1].row[0].ANEXP_BDGAMT}")
                }

                launch {
                    val testData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2021", actvName = "입법정보지원").await()
                    Log.d("Success", "2020 ${testData.njzofberazvhjncha[1].row[0].ANEXP_BDGAMT}")
                }

                launch {
                    val testData = RetrofitClient.getBudgetService().getDetailBusiness(fsclYY = "2022", actvName = "입법정보지원").await()
                    Log.d("Success", "2020 ${testData.njzofberazvhjncha[1].row[0].ANEXP_BDGAMT}")
                }

                binding.visualPanel.panelState = PanelState.COLLAPSED
                binding.chartScroll.fullScroll(ScrollView.FOCUS_UP)
            }
        })



        // floating action button 설정
        binding.fab.setOnClickListener {
            binding.visualPanel.panelState = PanelState.EXPANDED
        }

        initBarChart()
        setBarChartData()
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

    private suspend fun hostRetrofitClient(fsclYY : String, unitName : String) : List<Row> {
        // retrofit 객체 선언
        val retrofit = RetrofitClient.initCongressRetrofit()
        Log.d("LOG", "fsclYY : $fsclYY, unitName : $unitName")
        val budgetDetailApi = retrofit.create(RetrofitClient.BudgetApi::class.java)

        var totalData = listOf<Row>()

        budgetDetailApi.getDetailBusiness(fsclYY = fsclYY, actvName = unitName)
            .enqueue(object : retrofit2.Callback<BudgetResponseData> {
                override fun onResponse(
                    call: Call<BudgetResponseData>,
                    response: Response<BudgetResponseData>,
                ) {
                    // response 받은 최종 데이터
                    totalData = response.body()!!.njzofberazvhjncha[1].row

                    when (fsclYY) {
                        "2019" -> totalData.forEach { annualBudgetList[0]?.add( it ) }
                        "2020" -> totalData.forEach { annualBudgetList[1]?.add( it ) }
                        "2021" -> totalData.forEach { annualBudgetList[2]?.add( it ) }
                        "2022" -> totalData.forEach { annualBudgetList[3]?.add( it ) }
                    }

                    if(annualBudgetList[0]!!.isNotEmpty() && annualBudgetList[1]!!.isNotEmpty()
                        && annualBudgetList[2]!!.isNotEmpty() && annualBudgetList[3]!!.isNotEmpty()) {
                        transformData()
                        }
                }

                override fun onFailure(call: Call<BudgetResponseData>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                }
            })
        return totalData
    }

    private fun transformData() {

        val budgetGraphData = ArrayList<BarEntry>()
        for (i in 0.. annualBudgetList.count())
            budgetGraphData.add(BarEntry((2019+i).toFloat(), annualBudgetList[i]!!.sumOf { it.ANEXP_BDGAMT }.toFloat()))
    }


    private fun initBarChart() {
        // 차트 회색 배경 설정 (default = false)
        binding.customBarChart.setDrawGridBackground(false)
        // 막대 그림자 설정 (default = false)
        binding.customBarChart.setDrawBarShadow(false)
        // 차트 테두리 설정 (default = false)
        binding.customBarChart.setDrawBorders(false)

        val description = Description()
        // 오른쪽 하단 모서리 설명 레이블 텍스트 표시 (default = false)
        description.isEnabled = false
        binding.customBarChart.description = description

        // X, Y 바의 애니메이션 효과
        binding.customBarChart.animateY(1000)
        binding.customBarChart.animateX(1000)

        // 바텀 좌표 값
        val xAxis: XAxis = binding.customBarChart.xAxis
        // x축 위치 설정
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        // 그리드 선 수평 거리 설정
        xAxis.granularity = 1f
        // x축 텍스트 컬러 설정
        xAxis.textColor = Color.RED
        // x축 선 설정 (default = true)
        xAxis.setDrawAxisLine(false)
        // 격자선 설정 (default = true)
        xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = binding.customBarChart.axisLeft
        // 좌측 선 설정 (default = true)
        leftAxis.setDrawAxisLine(false)
        // 좌측 텍스트 컬러 설정
        leftAxis.textColor = Color.BLUE

        val rightAxis: YAxis = binding.customBarChart.axisRight
        // 우측 선 설정 (default = true)
        rightAxis.setDrawAxisLine(false)
        // 우측 텍스트 컬러 설정
        rightAxis.textColor = Color.GREEN

        // 바차트의 타이틀
        val legend: Legend = binding.customBarChart.legend
        // 범례 모양 설정 (default = 정사각형)
        legend.form = Legend.LegendForm.LINE
        // 타이틀 텍스트 사이즈 설정
        legend.textSize = 20f
        // 타이틀 텍스트 컬러 설정
        legend.textColor = Color.BLACK
        // 범례 위치 설정
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        // 범례 방향 설정
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        // 차트 내부 범례 위치하게 함 (default = false)
        legend.setDrawInside(false)
    }

    private fun initPieChart() {
        binding.customPieChart.setUsePercentValues(true)
        binding.customPieChart.description.isEnabled = false
        binding.customPieChart.setExtraOffsets(5F, 10F, 5F, 5F)

        binding.customPieChart.dragDecelerationFrictionCoef = 0.95f

        binding.customPieChart.isDrawHoleEnabled = false
        binding.customPieChart.setHoleColor(Color.WHITE)
        binding.customPieChart.transparentCircleRadius = 61f

        val yValues = ArrayList<PieEntry>()

        yValues.add(PieEntry(34f, "단위사업1"))
        yValues.add(PieEntry(23f, "단위사업2"))
        yValues.add(PieEntry(14f, "단위사업3"))
        yValues.add(PieEntry(35f, "단위사업4"))
        yValues.add(PieEntry(40f, "단위사업5"))

        val description = Description()
        description.text = "세계 국가" //라벨

        description.textSize = 15f
        binding.customPieChart.description = description

        val dataSet = PieDataSet(yValues, "Countries")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.JOYFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.YELLOW)

        binding.customPieChart.data = data
    }

    private fun animateGraph() {
        binding.customPieChart.animateY(1000, Easing.EaseInOutCubic) //애니메이션
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

    private fun setBarChartData() {
        // Zoom In / Out 가능 여부 설정
        binding.customBarChart.setScaleEnabled(false)

        val title = "예산현액 추이"

        val barDataSet = BarDataSet(graphValueList, title)
        // 바 색상 설정 (ColorTemplate.LIBERTY_COLORS)
        barDataSet.setColors(
            Color.rgb(207, 248, 246), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187),
            Color.rgb(118, 174, 175), Color.rgb(42, 109, 130))

        val data = BarData(barDataSet)
        binding.customBarChart.data = data
        binding.customBarChart.invalidate()
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