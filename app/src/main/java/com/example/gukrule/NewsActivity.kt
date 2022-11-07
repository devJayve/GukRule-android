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
import android.widget.TextView
import androidx.activity.OnBackPressedDispatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.gukrule.adapter.KeywordsAdapter
import com.example.gukrule.adapter.NewsAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.article.ArticleItemDecorator
import com.example.gukrule.databinding.ActivityNewsBinding

/* NewsActivity - 최신순으로 정렬, 10개씩 추가 업로드 기능 */
class NewsActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    lateinit var newsAdapter: NewsAdapter
    lateinit var newsTextView: TextView
    var newsData = mutableListOf<Article>()

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

        newsData.apply {
            add(
                Article(
                    id = 1,
                    budgetKey = "국회활동관련단체지원",
                    title = "윤 대통령 '보육 책임' 말하고선... 국공립 어린이집 예산은 19% 삭감",
                    image = R.drawable.article1,
                    content = "어린이집 확충 19%↓ 시설 개선 10%↓\n" +
                            "600억 밑으로 떨어진 건 5년만에 처음\n" +
                            "'부모급여' 통해 보육 기조 '가정양육'으로?\n",
                    date = "2022-10-10 09:00"
                ),
            )
            add(
                Article(
                    id = 2,
                    budgetKey = "사무처 인건비",
                    title = "6년째 공회전 북한인권재단... 텅 빈 사무실 임대료만 24억",
                    image = R.drawable.article2,
                    content = "이사회 구성 지연으로 출범 못 한 북한인권재단에 예산 24억 들어... 통일부, 마포구 700평 규모 사무실 임차했지만 23개월만에 철수",
                    date = "2022-10-11 09:00"
                ),
            )
            add(
                Article(
                    id = 3,
                    budgetKey = "의회운영지원",
                    title = "아산시 비정규직지원센터 인력·예산 반토막 논란",
                    image = R.drawable.article3,
                    content ="시 민간위탁 동의안 인력·예산 현행 대비 50% 축소 계획\n" +
                            "센터 사업 대폭 축소 불가피... 동의안 의회 통과 험로 예상",
                    date = "2022-10-12 09:00"
                ),
            )
            add(
                Article(
                    id = 4,
                    budgetKey = "국회활동관련단체지원",
                    title = "윤 대통령 '보육 책임' 말하고선... 국공립 어린이집 예산은 19% 삭감",
                    image = R.drawable.article1,
                    content = "어린이집 확충 19%↓ 시설 개선 10%↓\n" +
                            "600억 밑으로 떨어진 건 5년만에 처음\n" +
                            "'부모급여' 통해 보육 기조 '가정양육'으로?\n",
                    date = "2022-10-10 09:00"
                ),
            )
            add(
                Article(
                    id = 5,
                    budgetKey = "사무처 인건비",
                    title = "6년째 공회전 북한인권재단... 텅 빈 사무실 임대료만 24억",
                    image = R.drawable.article2,
                    content = "이사회 구성 지연으로 출범 못 한 북한인권재단에 예산 24억 들어... 통일부, 마포구 700평 규모 사무실 임차했지만 23개월만에 철수",
                    date = "2022-10-11 09:00"
                ),
            )
            add(
                Article(
                    id = 6,
                    budgetKey = resources.getString(R.string.keyword1_name),
                    title = "사무처 인건비 관련 기사2",
                    image = R.drawable.keyword_image,
                    content = "내용",
                    date = "0000-00-00 00:00:00"
                ),
            )
            add(
                Article(
                    id = 7,
                    budgetKey = resources.getString(R.string.keyword2_name),
                    title = "의회 운영지원 관련 기사2",
                    image = R.drawable.keyword_image,
                    content = "내용",
                    date = "0000-00-00 00:00:00"
                ),
            )
            add(
                Article(
                    id = 8,
                    budgetKey = resources.getString(R.string.keyword3_name),
                    title = "관련단체 지원 관련 기사2",
                    image = R.drawable.keyword_image,
                    content = "내용",
                    date = "0000-00-00 00:00:00"
                ),
            )
            add(
                Article(
                    id = 9,
                    budgetKey = resources.getString(R.string.keyword4_name),
                    title = "국가 물류 관련 기사2",
                    image = R.drawable.keyword_image,
                    content = "내용",
                    date = "0000-00-00 00:00:00"
                ),
            )
            add(
                Article(
                    id = 10,
                    budgetKey = resources.getString(R.string.keyword5_name),
                    title = "경상 의료비 관련 기사2",
                    image = R.drawable.keyword_image,
                    content = "내용",
                    date = "0000-00-00 00:00:00"
                ),
            )

            newsAdapter.newsData = newsData
            newsAdapter.notifyDataSetChanged()
        }
    }
}