package com.example.gukrule.ui.feed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gukrule.*
import com.example.gukrule.PageUrl.pageUrl
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.adapter.KeywordsAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.article.ArticleItemDecorator
import com.example.gukrule.databinding.FragmentFeedBinding
import com.example.gukrule.keyword.Keyword
import com.example.gukrule.keyword.KeywordItemDecorator
import com.example.gukrule.retrofit.CrawlingNewList
import com.example.gukrule.retrofit.CrawlingRequestData
import com.example.gukrule.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val keywordData = mutableListOf<Keyword>()
    private lateinit var mainActivity : MainActivity
    private val binding get() = _binding!!

    // Adapter
    private lateinit var keywordsAdapter : KeywordsAdapter
    private lateinit var articlesAdapter : ArticlesAdapter

    private var keyword: String? = null // 키워드
    private var pageCount = 1  // 현재 페이지


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        articlesAdapter = ArticlesAdapter()
        keywordsAdapter = KeywordsAdapter()

        val linearLayoutManager = LinearLayoutManager(mainActivity)
        linearLayoutManager.orientation = GridLayoutManager.VERTICAL

        binding.keywordRecycler.adapter = keywordsAdapter
        binding.keywordRecycler.addItemDecoration(KeywordItemDecorator(40))

        binding.articleRecycler.adapter = articlesAdapter
        binding.articleRecycler.layoutManager = linearLayoutManager
        binding.articleRecycler.addItemDecoration(ArticleItemDecorator(1, Color.GRAY))

        // 더미 데이터
        keywordData.apply {
            add(
                Keyword(
                    id = 1,
                    name = "국회행정지원 예산처 기본경비",
                ),
            )
            add(
                Keyword(
                    id = 2,
                    name = "의정활동지원 의정지원",
                ),
            )
            add(
                Keyword(
                    id = 3,
                    name = "입법조사처운영 입법조사처 정보화",
                ),
            )
            add(
                Keyword(
                    id = 4,
                    name = "예산정책처운영 국가재정경제분석및의안비용추계",
                ),
            )
            add(
                Keyword(
                    id = 5,
                    name = "국회도서관운영 전자도서관운영",
                ),
            )
            keywordsAdapter.keywordData = keywordData
        }

        // Fragment to Activity
        binding.keywordArrow.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(this, KeywordActivity::class.java))
                finish()
            }
        }

        binding.articleArrow.setOnClickListener{
            mainActivity.moveToAllPost()
        }

        // articleRecyclerView item click -> ArticleVisualActivity
        articlesAdapter.setItemClickListener(object: ArticlesAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                // fragment에서 activity로 articleUrl(String?)을 넘겨야하는데 잘 안됨
//                // 액티비티로 페이지 이동, articleUrl 값은 가져온 상태
                pageUrl = articlesAdapter.articleData[position].image
                Log.d("LOG", "img url : $pageUrl")
                mainActivity.moveToArticle("test", "test")
            }
        })

        // keywordRecyclerView item click -> request data keyword setting
        keywordsAdapter.setItemClickListener(object: KeywordsAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                keyword = keywordData[position].name
                Log.d("keyword", ""+keyword)
                // keyword도 바뀜
                // articleList를 clear하고 페이지 다시 그림

            }
        })

        binding.feedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.measuredHeight - v.getChildAt(0).measuredHeight) {
                Log.i("TAG", "BOTTOM SCROLL")
            }
        })

        // 초기 기사 불러오기
        loadArticlePost()

        return root
    }

    private fun loadArticlePost() {
        val requestData = CrawlingRequestData(userIdx = 218, keyword = keyword, page = 1)
        val articleApi = RetrofitClient.initLocalRetrofit().create(RetrofitClient.CrawlingNewsApi::class.java)
        articleApi.getCrawlingNews(
            jwtKey = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyMTgsImlhdCI6MTY2Nzk4OTQ1MCwiZXhwIjoxNjY5NDYwNjc5fQ.iYa-I-ExdJoF6LSJ_zlPXB4d49lK_RitfeWhNQnKTDE" ,
            crawlingRequestData = requestData).enqueue(object : Callback<CrawlingNewList> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<CrawlingNewList>, response: Response<CrawlingNewList>) {
                    Log.d("LOG", "function - loadArticlePost()")
                    Log.d("LOG", "code : ${response.body()!!.code}")
                    if (response.isSuccessful && response.body()!!.code == 1000) {
                        articlesAdapter.run {
                            val tmpKeyword = response.body()!!.result[10][0]
                            response.body()!!.result.forEachIndexed { index, it ->
                                if(index != 10) {
                                    articlesAdapter.articleData.add(
                                        Article(
                                            budgetKey = tmpKeyword,
                                            title = it[0],
                                            date = it[1],
                                            image = it[2],
                                            content = it[3],
                                            url = it[4]
                                        ))
                                }
                            }
                            notifyDataSetChanged()
                        }
                    }
                }
                override fun onFailure(call: Call<CrawlingNewList>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                }
            })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

