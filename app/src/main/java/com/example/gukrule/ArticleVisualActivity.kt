package com.example.gukrule

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.bumptech.glide.Glide
import com.example.gukrule.PageUrl.pageUrl
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.databinding.ActivityArticleVisualBinding
import com.example.gukrule.retrofit.*
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
        val articleUrl = intent.getStringExtra("articleUrl")
        val imgUrl = intent.getStringExtra("imgUrl")
        Log.d("check: ", keyword!!)
        Log.d("check: ", articleUrl!!)
        Log.d("check: ", imgUrl!!)

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

        Log.d("LOG","articleUrl : $articleUrl & $keyword")
        val requestData = CrawlingArticleRequestData(userIdx = 218, url = articleUrl, keyword = "국회행정지원 예산처 기본경비")
        val articleApi: RetrofitClient.CrawlingArticleApi = RetrofitClient.initLocalRetrofit().create(
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
                    Log.d("success", response.body()!!.code.toString())
                    Log.d("success", response.body()!!.message)
                    Log.d("success", response.body()!!.result.toString())
                    article = response.body()!!.result
                    val fullArticleText : String = article.articleText!!.substring(1).trim().replace("\\r\\n|\\r|\\n|\\n\\r".toRegex(),"")
                    Log.d("LOG", "delete space : $fullArticleText")

                    hostSummaryApi(fullArticleText)

                    //이미지 url 링크 받아서 인터넷으로 로딩해서 띄우기
                    Glide.with(article_visual)
                        .load(imgUrl)
                        .error(R.drawable.img_load_failed)
                        .into(findViewById(R.id.article_image))

//                    binding.articleSummaryHeader.text = "상세 기사"
                    binding.articleSheetButton.text = "기사 데이터 그래프"
                    binding.articleSheetButton.visibility = View.VISIBLE
                    binding.articleSheetButton.background.apply { R.drawable.article_bottom_sheet_button }
                    binding.articleSheetButton.drawableState.apply { R.drawable.article_chart }
                    binding.articleSummaryIcon.findViewById<ImageView>(R.drawable.article_summary_icon)

                    // by crawling api
                    binding.articleTitle.text = article.title
                    binding.articleFullArticle.text = fullArticleText
                }
                override fun onFailure(call: Call<CrawlingArticle>, t: Throwable) {
                }
            })
        // 기사 내용 텍스트 뷰로 전

    }

    private fun hostSummaryApi(fullArticleText :String) {
        Log.d("LOG", "host summary : $fullArticleText")
        val summaryData = SummaryRequestData(inputs = fullArticleText)
        val summaryApi: RetrofitClient.SummaryNewsApi = RetrofitClient.initSummaryRetrofit().create(
            RetrofitClient.SummaryNewsApi::class.java)
        summaryApi.postSummaryNews(
            Authorization = "Bearer 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",
            summaryRequestData = summaryData,
        )
            .enqueue(object : Callback<SummaryArticle>{
                override fun onResponse(
                    call: Call<SummaryArticle>,
                    response: Response<SummaryArticle>
                ) {
                    Log.d("LOG","summaryText: " + response.code().toString())
                    val summaryText: String = response.body()!!.generatedList[0].generated_text
                    Log.d("summaryText: ", summaryText)
                    binding.articleVisualSummary.text = summaryText
                }
                override fun onFailure(call: Call<SummaryArticle>, t: Throwable) {
                }
            })

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



