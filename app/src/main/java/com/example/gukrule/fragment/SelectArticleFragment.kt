package com.example.gukrule.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gukrule.SignUpActivity
import com.example.gukrule.adapter.SelectRVAdapter
import com.example.gukrule.databinding.FragmentSelectArticleBinding
import com.example.gukrule.databinding.ItemSelectArticleBinding
import com.example.gukrule.retrofit.SelectedArticleData
import com.example.gukrule.retrofit.adapter.KeywordRVAdapter
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.item_select_article.*
import org.w3c.dom.Text


class SelectArticleFragment : Fragment() {

    private lateinit var signUpActivity: SignUpActivity
    private lateinit var selectAdapter: SelectRVAdapter
    private lateinit var gridManger: GridLayoutManager
    private var _binding: FragmentSelectArticleBinding? = null
    private var _itemBinding: ItemSelectArticleBinding? = null
    private val binding get() = _binding!!
    private val itemBinding get() = _itemBinding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpActivity = activity as SignUpActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectArticleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dummyDataList = arrayListOf(
            "국회행정지원 조사처 기본경비",
            "의정활동지원 의정지원",
            "의정활동지원 예비금",
            "국회도서관운영 입법정보지원",
            "국회사무처운영 입법정보화",
            "국회사무처운영 의회운영지원"
        )

        selectAdapter = SelectRVAdapter(dummyDataList, dummyDataList)

        gridManger = GridLayoutManager(signUpActivity, 2)
        gridManger.orientation = GridLayoutManager.VERTICAL

        binding.selectArticleGrid.apply {
            layoutManager = gridManger
            adapter = selectAdapter
        }

        selectAdapter.setItemClickListener(object : SelectRVAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                Log.d("TAG", "select item")
            }
        })

        return root
    }


}