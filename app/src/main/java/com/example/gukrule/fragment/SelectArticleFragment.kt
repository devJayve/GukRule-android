package com.example.gukrule.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gukrule.LoginActivity
import com.example.gukrule.MainActivity
import com.example.gukrule.SignUpActivity
import com.example.gukrule.adapter.SelectRVAdapter
import com.example.gukrule.databinding.FragmentSelectArticleBinding
import com.example.gukrule.databinding.ItemSelectArticleBinding
import com.example.gukrule.retrofit.RetrofitClient
import com.example.gukrule.retrofit.SelectedArticleData
import com.example.gukrule.retrofit.SelectedArticleResponse
import retrofit2.Call
import retrofit2.Response


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

        binding.signUpSubmitBtn.setOnClickListener {
            registerSelectedArticleApi()
        }

        return root
    }

    private fun registerSelectedArticleApi(){
        val selectedArticleData = SelectedArticleData(
            userIdx = ,
            keyword1 = ,
            keyword2 = ,
            keyword3 = ,
            keyword4 = ,
            keyword5 = ,
        )
        val retrofit = RetrofitClient.initLocalRetrofit()
        val registerApiArticle = retrofit.create(RetrofitClient.RegisterApiArticle::class.java)
        registerApiArticle.getRegisterArticleData(selectedArticleData = selectedArticleData)
            .enqueue(object: retrofit2.Callback<SelectedArticleResponse>{
                override fun onResponse(
                    call: Call<SelectedArticleResponse>,
                    response: Response<SelectedArticleResponse>
                ) {
                    if(response.body()!!.isSuccess && response.body()!!.code == 1000){
                        val intent = Intent(signUpActivity, LoginActivity::class.java) // 로그인 페이지로 전환
                        startActivity(intent)
                        response.body()!!.result
                    }
                }

                override fun onFailure(call: Call<SelectedArticleResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}