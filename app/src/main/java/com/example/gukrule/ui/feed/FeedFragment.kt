package com.example.gukrule.ui.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.ArticleVisualActivity
import com.example.gukrule.KeywordActivity
import com.example.gukrule.NewsActivity
import com.example.gukrule.R
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.article.ArticleItemDecorator
import com.example.gukrule.databinding.FragmentFeedBinding
import com.example.gukrule.keyword.Keyword
import com.example.gukrule.keyword.KeywordItemDecorator
import com.example.gukrule.retrofit.CrawlingNewList
import com.example.gukrule.retrofit.CrawlingRequestData
import com.example.gukrule.retrofit.RetrofitClient
import com.example.gukrule.retrofit.adapter.KeywordsAdapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val keywordData = mutableListOf<Keyword>()
    private var articleList = listOf<List<String>>()
//    lateinit var keywordsAdapter: KeywordsAdapter
//    lateinit var articlesAdapter: ArticlesAdapter
    private var word: String? = null
    private var pageInt: Int = 1
    private val binding get() = _binding!!

    // onCreateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val articlesAdapter = ArticlesAdapter()
        val keywordsAdapter = KeywordsAdapter()

        val keywordRecyclerView: RecyclerView = binding.keywordRecycler
        keywordRecyclerView.adapter = keywordsAdapter
        keywordRecyclerView.addItemDecoration(KeywordItemDecorator(30))

        val articleRecyclerView: RecyclerView = binding.articleRecycler
        articleRecyclerView.adapter = articlesAdapter
        articleRecyclerView.addItemDecoration(ArticleItemDecorator(1, Color.GRAY))

        // userIdx = getUserIdx()
        val requestData = CrawlingRequestData(userIdx = 218, keyword = word, page = pageInt)
        var articleApi: RetrofitClient.CrawlingApi = RetrofitClient.initLocalRetrofit()!!.create(RetrofitClient.CrawlingApi::class.java)
        articleApi.getCrawlingNews(
            // jwtKey = getUserToken()
            jwtKey = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyMTgsImlhdCI6MTY2Nzk4OTQ1MCwiZXhwIjoxNjY5NDYwNjc5fQ.iYa-I-ExdJoF6LSJ_zlPXB4d49lK_RitfeWhNQnKTDE" ,
            crawlingRequestData = requestData,
            )
            .enqueue(object : Callback<CrawlingNewList> {
                override fun onResponse(
                    call: Call<CrawlingNewList>?,
                    response: Response<CrawlingNewList>?,
                ) {
                    val pCode = response?.body()!!.code
                    var errMessage : String? = "에러메시지"
                    if (pCode == 1000) {
                        Log.d("success", response?.body()!!.code.toString())
                        Log.d("success", response?.body()!!.message.toString())
                        Log.d("success", response?.body()!!.result!![0].toString())
                        articleList = response?.body()!!.result!!

                        val articleData = mutableListOf<Article>()
                        for(i: Int in 0..9) {
                            articleData.add(
                                Article(
                                    id = i + 1,
                                    budgetKey = "",
                                    title = articleList[i][0],
                                    date = articleList[i][1],
                                    image = articleList[i][2],
                                    content = articleList[i][3],
                                ),
                            )
                        }
                        articlesAdapter.articleData = articleData
                        articlesAdapter.notifyDataSetChanged()
                    } else {
                        when (pCode) {
                            2001 -> errMessage = pCode.toString()+"JWT를 입력해주세요"
                            2002 -> errMessage = pCode.toString()+"유효하지 않은 JWT입니다."
                            2003 -> errMessage = pCode.toString()+"권한이 없는 유저의 접근"
                            2044 -> errMessage = pCode.toString()+"page를 입력해주세요"
                            4000 -> errMessage = pCode.toString()+"데이터베이스 연결에 실패하였습니다."
                        }
                        Toast.makeText(view?.context,
                            "${pCode}\n${errMessage}",
                            Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CrawlingNewList>?, t: Throwable?) {
                    Log.d("failure", t?.message.toString())
                }
            })

        // 더미 데이터
        keywordData.apply {
            add(
                Keyword(
                    id = 1,
                    name = resources.getString(R.string.keyword1_name),
                ),
            )
            add(
                Keyword(
                    id = 2,
                    name = resources.getString(R.string.keyword2_name),
                ),
            )
            add(
                Keyword(
                    id = 3,
                    name = resources.getString(R.string.keyword3_name),
                ),
            )
            add(
                Keyword(
                    id = 4,
                    name = resources.getString(R.string.keyword4_name),
                ),
            )
            add(
                Keyword(
                    id = 5,
                    name = resources.getString(R.string.keyword5_name),
                ),
            )

            keywordsAdapter.keywordData = keywordData
            keywordsAdapter.notifyDataSetChanged()
        }

        // 스크롤 다운 시 추가 호출(일단 보류)


        // Fragment to Activity
        val keywordArrow: ImageButton = binding.keywordArrow
        keywordArrow.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(this, KeywordActivity::class.java))
                finish()
            }
        }
        val articleArrow: ImageButton = binding.articleArrow
        articleArrow.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(this, NewsActivity::class.java))
                finish()
            }
        }

        // articleRecyclerView item click -> ArticleVisualActivity
        articlesAdapter.setItemClickListener(object: ArticlesAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                requireActivity().run{
                    startActivity(Intent(this, ArticleVisualActivity::class.java))
                    finish()
                }
                val articleUrl : String?
                articleUrl = "${articleList[position][4]}"
                Log.d("article", ""+articleUrl)
            }
            // fragment에서 activity로 articleUrl(String?)을 넘겨야하는데 잘 안됨
            // 액티비티로 페이지 이동, articleUrl 값은 가져온 상태
        })

        // keywordRecyclerView item click -> request data keyword setting
        keywordsAdapter.setItemClickListener(object: KeywordsAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                word = "${keywordData[position].name}"
                Log.d("keyword", ""+word)
                // keyword도 바뀜
                // articleList를 clear하고 페이지 다시 그림?
            }
        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

