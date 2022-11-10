package com.example.gukrule

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.widget.TextView
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.retrofit.adapter.KeywordsAdapter
import com.example.gukrule.retrofit.adapter.NewsAdapter
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
class NewsActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private var newsList = listOf<List<String>>()
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsTextView: TextView
    private var pageInt: Int = 1

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsAdapter = NewsAdapter()

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

        val newsRecyclerView = binding!!.newsRecycler
        newsRecyclerView.adapter = newsAdapter
        newsRecyclerView.addItemDecoration(ArticleItemDecorator(1, Color.GRAY))

        val newsTextView = binding!!.newsDescription
        val textData: String = newsTextView.text.toString()
        val builder = SpannableStringBuilder(textData)
        /* 볼드체 적용 */
        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, 18, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        /* 크기 1.05배 적용 */
        val sizeBigSpan = RelativeSizeSpan(1.05f)
        builder.setSpan(sizeBigSpan, 18, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        /* 글자 색상 변경 적용 */
        val colorBlueSpan = ForegroundColorSpan(Color.BLUE)
        builder.setSpan(colorBlueSpan, 18, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        newsTextView.text = builder

        // userIdx = getUserIdx()
        val requestData = CrawlingRequestData(userIdx = 218, keyword = null, page = pageInt)
        var articleApi: RetrofitClient.CrawlingNewsApi = RetrofitClient.initLocalRetrofit()!!.create(
            RetrofitClient.CrawlingNewsApi::class.java)
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
                    Log.d("success", response?.body()!!.code.toString())
                    Log.d("success", response?.body()!!.message.toString())
                    Log.d("success", response?.body()!!.result!![0].toString())
                    newsList = response?.body()!!.result!!

                    val newsData = mutableListOf<Article>()
                    for(i: Int in 0..9) {
                        newsData.add(
                            Article(
                                id = i + 1,
                                budgetKey = "",
                                title = newsList[i][0],
                                date = newsList[i][1],
                                image = newsList[i][2],
                                content = newsList[i][3],
                            ),
                        )
                    }
                    newsAdapter.newsData = newsData
                    newsAdapter.notifyDataSetChanged()

                }

                override fun onFailure(call: Call<CrawlingNewList>?, t: Throwable?) {
                }
            })
    }
}