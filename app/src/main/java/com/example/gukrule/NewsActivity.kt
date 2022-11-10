package com.example.gukrule

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.adapter.NewsAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.article.ArticleItemDecorator
import com.example.gukrule.databinding.ActivityNewsBinding
import com.example.gukrule.retrofit.CrawlingNewList
import com.example.gukrule.retrofit.CrawlingRequestData
import com.example.gukrule.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* NewsActivity - 최신순으로 정렬, 10개씩 추가 업로드 기능 */
class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private lateinit var articlesAdapter : NewsAdapter
    private var keyword: String? = null
    private var pageCount = 1
    private var isLoading = false

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isKeywordExist = intent.getBooleanExtra("isKeywordExist", false)
        if(isKeywordExist) {
            keyword = intent.getStringExtra("keyword")
        }


        articlesAdapter = NewsAdapter()

        /* toolbar 클릭 범위를 전체에서 아이콘으로 줄이기 */
        setSupportActionBar(findViewById(R.id.article_toolbar))
        supportActionBar?.apply {
            setIcon(R.drawable.back_arrow)
            setDisplayUseLogoEnabled(true)
            setTitle(R.string.subtitle_article)
        }
        val articleToolbar = binding.articleToolbar
        articleToolbar.setOnClickListener{
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }

        binding.newsRecycler.adapter = articlesAdapter
        binding.newsRecycler.addItemDecoration(ArticleItemDecorator(1, Color.GRAY))
        loadArticlePost()
        initScrollListener()
    }

    private fun loadArticlePost() {
        isLoading = true
            val requestData = CrawlingRequestData(userIdx = 218, keyword = keyword, page = pageCount)
            val articleApi = RetrofitClient.initLocalRetrofit().create(RetrofitClient.CrawlingNewsApi::class.java)
            articleApi.getCrawlingNews(
                jwtKey = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyMTgsImlhdCI6MTY2Nzk4OTQ1MCwiZXhwIjoxNjY5NDYwNjc5fQ.iYa-I-ExdJoF6LSJ_zlPXB4d49lK_RitfeWhNQnKTDE" ,
                crawlingRequestData = requestData).enqueue(object : Callback<CrawlingNewList> {
                override fun onResponse(call: Call<CrawlingNewList>, response: Response<CrawlingNewList>) {
                    Log.d("LOG", "function - loadArticlePost()")
                    Log.d("TAG","response code : ${response.body()!!.code}")
                    if (response.isSuccessful && response.body()!!.code == 1000) {
                        Log.d("TAG","response code : ${response.code()}")
                        Log.d("TAG","response code : ${response.body()!!.result}")
                        articlesAdapter.run {
                            response.body()!!.result.forEachIndexed { index, it ->
                                if(index == 10) {
                                    keyword = it[0]
                                } else {
                                    initializeArticle(
                                        Article(
                                            budgetKey = keyword,
                                            title = it[0],
                                            date = it[1],
                                            image = it[2],
                                            content = it[3],
                                            url = it[4]
                                        ))
                                }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<CrawlingNewList>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                }
            })
    }

    // 리사이클러뷰에 더 보여줄 데이터를 로드하는 경우
    private fun loadMoreArticle() {
        if(!isLoading) {
            val requestData = CrawlingRequestData(userIdx = 218, keyword = keyword, page = getPage())
            val articleApi = RetrofitClient.initLocalRetrofit()
                .create(RetrofitClient.CrawlingNewsApi::class.java)
            articleApi.getCrawlingNews(
                jwtKey = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyMTgsImlhdCI6MTY2Nzk4OTQ1MCwiZXhwIjoxNjY5NDYwNjc5fQ.iYa-I-ExdJoF6LSJ_zlPXB4d49lK_RitfeWhNQnKTDE",
                crawlingRequestData = requestData).enqueue(object : Callback<CrawlingNewList> {
                override fun onResponse(
                    call: Call<CrawlingNewList>,
                    response: Response<CrawlingNewList>
                ) {
                    Log.d("LOG", "function - loadMoreArticle()")
                    if (response.isSuccessful) {
                        articlesAdapter.run {
                            response.body()!!.result.forEachIndexed { index, it ->
                                if (index == 10) {
                                    keyword = it[0]
                                } else {
                                    addArticleData(
                                        Article(
                                            budgetKey = keyword,
                                            title = it[0],
                                            date = it[1],
                                            image = it[2],
                                            content = it[3],
                                            url = it[4]
                                        ))
                                }
                            }
                        }
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<CrawlingNewList>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                }
            })
        }
    }

    private fun initScrollListener() {
        binding.newsRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("LOG", "scroll to bottom")
                    loadMoreArticle()
                    isLoading = true
                }
            }
        })
    }

    private fun getPage(): Int {
        pageCount += 15
        return pageCount
    }
}