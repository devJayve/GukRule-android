package com.example.gukrule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.bumptech.glide.Glide
import com.example.gukrule.PageUrl.pageUrl
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.databinding.ActivityArticleVisualBinding
import com.example.gukrule.retrofit.*
import com.example.gukrule.ui.feed.FeedFragment
import kotlinx.android.synthetic.main.activity_article_visual.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// FeedFragment에서 가져와야하는 정보
// imgUrl, title, summaryText, *pageUrl  <= articleList[position]

class ArticleVisualActivity : AppCompatActivity() {
    private var _binding: ActivityArticleVisualBinding? = null
    private val binding get() = _binding!!
    private var article = ArticleResult()

    //private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArticleVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val keyword = intent.getStringExtra("keyword")
        Log.d("keyword: ", ""+keyword)
        /* toolbar 클릭 범위를 전체에서 아이콘으로 줄이기 */
        setSupportActionBar(findViewById(R.id.article_visual_toolbar))
        supportActionBar?.apply {
            setIcon(R.drawable.back_arrow)
            setDisplayUseLogoEnabled(true)
//            setTitle(R.string.subtitle_article)
            setTitle("상세 기사")
        }
        val articleToolbar = binding.articleVisualToolbar
        articleToolbar.setOnClickListener{
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }

        // userIdx = getUserIdx()
        val requestData = CrawlingArticleRequestData(userIdx = 218, url = "")
        var articleApi: RetrofitClient.CrawlingArticleApi = RetrofitClient.initLocalRetrofit().create(
            RetrofitClient.CrawlingArticleApi::class.java)
        articleApi.getCrawlingArticle(
            // jwtKey = getUserToken()
            jwtKey = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyMTgsImlhdCI6MTY2Nzk4OTQ1MCwiZXhwIjoxNjY5NDYwNjc5fQ.iYa-I-ExdJoF6LSJ_zlPXB4d49lK_RitfeWhNQnKTDE" ,
            crawlingArticleRequestData = requestData,
        )
            .enqueue(object : Callback<CrawlingArticle> {
                @SuppressLint("ResourceType")
                override fun onResponse(
                    call: Call<CrawlingArticle>,
                    response: Response<CrawlingArticle>
                ) {
                    Log.d("success", response?.body()!!.code.toString())
                    Log.d("success", response?.body()!!.message.toString())
                    Log.d("success", response?.body()!!.result!!.toString())
                    article = response?.body()!!.result!!

                    //이미지 url 링크 받아서 인터넷으로 로딩해서 띄우기    // articleList[position]에서 추출
                    Glide.with(article_visual)
                        .load(pageUrl)
                        .error(R.drawable.img_load_failed)
                        .into(findViewById(R.id.article_image))

                    binding.articleTitle.text = "기사제목"       // articleList[position]에서 추출
                    binding.articleSummary.text = "기사요약"     // articleList[position]에서 추출
                    binding.articleFullArticle.text = article.articleText

                }
                override fun onFailure(call: Call<CrawlingArticle>?, t: Throwable?) {
                }
            })

        // 기사 내용 텍스트 뷰로 전달


        //제스쳐 감지
        //mDetector = GestureDetectorCompat(this, this)


    }

//    override fun onScroll(
//        event1: MotionEvent,
//        event2: MotionEvent,
//        distanceX: Float,
//        distanceY: Float
//    ): Boolean {
//        return true
//    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



