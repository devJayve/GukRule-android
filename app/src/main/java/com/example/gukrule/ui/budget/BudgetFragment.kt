package com.example.gukrule.ui.budget

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.gukrule.R
import com.example.gukrule.adapter.CarouselRVAdapter
import com.example.gukrule.databinding.FragmentBudgetBinding
import java.lang.Math.abs

class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setCarouselSlider()

        return root
    }

    private fun setCarouselSlider() {
        val margin = ((resources.displayMetrics.density) * 50).toInt()
        val viewPager = binding.budgetViewPager
        viewPager.apply {
            offscreenPageLimit = 3
            clipToPadding = false
            setPadding(margin, 0, margin, 0)
            getChildAt(0).overScrollMode=View.OVER_SCROLL_NEVER
        }

        val demoData = arrayListOf(
            "국회 예산 1입니다",
            "국회 예산 2입니다",
            "국회 예산 3입니다",
            "국회 예산 4입니다",
        )

        viewPager.adapter = CarouselRVAdapter(demoData)
        val compositePageTransformer = CompositePageTransformer()

        // 양 옆 위젯 사이즈 조정
        compositePageTransformer.addTransformer(ViewPager2.PageTransformer{ view: View, fl: Float ->
            val v = 1- kotlin.math.abs(fl)
            view.scaleY = 0.8f + v * 0.2f
        })

        // 위젯 간 Margin 조정
        compositePageTransformer.addTransformer(MarginPageTransformer((20 * Resources.getSystem().displayMetrics.density).toInt()))

        viewPager.setPageTransformer(compositePageTransformer)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}