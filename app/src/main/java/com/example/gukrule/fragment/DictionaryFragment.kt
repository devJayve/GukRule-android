package com.example.gukrule.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.gukrule.BudgetVisualActivity
import com.example.gukrule.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment() {

    private lateinit var budgetVisualActivity: BudgetVisualActivity
    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        budgetVisualActivity = activity as BudgetVisualActivity
    }

//    override fun onCreateView()

}