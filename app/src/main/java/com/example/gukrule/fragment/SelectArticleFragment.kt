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
    private val binding get() = _binding!!

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

        val userIdx = arguments?.getInt("userIdx", 0)!!
        val jwt = arguments?.getString("jwt", "")!!



        val dummyDataList = arrayListOf(
            "국회행정지원 조사처 기본경비",
            "의정활동지원 의정지원",
            "의정활동지원 예비금",
            "국회도서관운영 입법정보지원",
            "국회사무처운영 입법정보화",
            "국회사무처운영 의회운영지원"
        )

        val dummyDataUrl = arrayListOf(
            "https://imgnews.pstatic.net/image/469/2022/10/10/0000701258_001_20221010090104176.jpg?type=w647",
            "https://imgnews.pstatic.net/image/003/2022/11/10/NISI20221110_0001126847_web_20221110175530_20221110180006755.jpg?type=w647",
            "https://imgnews.pstatic.net/image/047/2015/05/23/IE001827293_STD_99_20150523114603.jpg?type=w647",
            "https://imgnews.pstatic.net/image/421/2022/08/31/0006309447_001_20220831184703144.jpg?type=w647",
            "https://imgnews.pstatic.net/image/030/2020/11/10/0002912265_001_20201110171713920.jpg?type=w647",
            "https://imgnews.pstatic.net/image/021/2022/09/19/0002531609_001_20220919115806431.jpg?type=w647",
        )

        selectAdapter = SelectRVAdapter(dummyDataList, dummyDataList,dummyDataUrl)

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
            Log.d("success", userIdx.toString())
            Log.d("success", "버튼 눌립니다")
            registerSelectedArticleApi(userIdx, jwt)
        }

        return root
    }

    private fun registerSelectedArticleApi(userIdx : Int, jwt : String){
        Log.d("LOG", "function - registerSelectedArticleApi")
        val selectedArticleData = SelectedArticleData(
            userIdx = userIdx,
            keyword1 = selectAdapter.sendDataList[0],
            keyword2 = selectAdapter.sendDataList[1],
            keyword3 = selectAdapter.sendDataList[2],
            keyword4 = selectAdapter.sendDataList[3],
            keyword5 = selectAdapter.sendDataList[4],
        )
        val retrofit = RetrofitClient.initLocalRetrofit()
        val registerArticleApi = retrofit.create(RetrofitClient.RegisterApiArticle::class.java)
        registerArticleApi.getRegisterArticleData(selectedArticleData = selectedArticleData, jwtKey = jwt)
            .enqueue(object: retrofit2.Callback<SelectedArticleResponse>{
                override fun onResponse(
                    call: Call<SelectedArticleResponse>,
                    response: Response<SelectedArticleResponse>
                ) {
                    if(response.body()!!.isSuccess && response.body()!!.code == 1000){

                        signUpActivity.moveToLogin()
                    }
                }

                override fun onFailure(call: Call<SelectedArticleResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}