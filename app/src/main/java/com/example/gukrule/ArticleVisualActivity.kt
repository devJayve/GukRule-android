package com.example.gukrule

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import com.bumptech.glide.Glide
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.databinding.ActivityArticleVisualBinding
import com.example.gukrule.ui.feed.FeedFragment

class ArticleVisualActivity : AppCompatActivity() {
    //GestureDetector.OnGestureListener
    private var _binding: ActivityArticleVisualBinding? = null
    private val binding get() = _binding!!

    //private lateinit var mDetector: GestureDetectorCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArticleVisualBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이미지 url 링크 받아서 인터넷으로 로딩해서 띄우기
        Glide.with(this)
            .load("https://imgnews.pstatic.net/image/469/2022/10/10/0000701258_001_20221010090104176.jpg?type=w647")
            .into(findViewById(R.id.article_image))

        // 기사 내용 텍스트 뷰로 전달
        binding.articleTitle.setText("윤 대통령 '보육 책임' 말하고선 ... 국공립 어린이집 예산은 19% 삭감")
        binding.articleSummary.setText("어린이집 확충 19%↓, 시설 개선 10%↓ \n 600억 밑으로 떨어진 건 5년 만에 처음 \n '부모급여' 통해 보육 기조 '가정양육'으로?")
        binding.articleFullArticle.setText(
            "어린이집 시설 개보수와 장비 지원 예산도 덩달아 줄었다. 윤 대통령이 지난달 27일 세종시의 국공립 어린이집을 방문해 '어린이집 환경 개선을 국정과제로 선정하겠다'고 언급한 것과 배치된다. "
                    + "\\n 어린이집 기능 보강 개선 예산은 34억7,300만 원으로 올해보다 10% 삭감됐다. 장애아동 시설 환경 개선 사업 예산(1억800만 원)도 10% 줄었다.\n"
                    + "일각에선 윤석열 정부가 국정과제로 내년 처음 시행하는 '부모급여(만 0·1세 각각 100만 원, 50만 원, 내년에는 각각 70만 원, 35만 원 지급)'를 위해 보육 관련 사업 예산을 대부분 삭감한 것으로 해석한다. 복지부는 영아수당을 확대한 부모급여 사업에 약 1조6,000억 원을 책정했다. 지난해(영아수당)보다 366% 증액된 규모다.어린이집 시설 개보수와 장비 지원 예산도 덩달아 줄었다. 윤 대통령이 지난달 27일 세종시의 국공립 어린이집을 방문해 '어린이집 환경 개선을 국정과제로 선정하겠다'고 언급한 것과 배치된다. \\n 어린이집 기능 보강 개선 예산은 34억7,300만 원으로 올해보다 10% 삭감됐다. 장애아동 시설 환경 개선 사업 예산(1억800만 원)도 10% 줄었다.\n"
                    + "일각에선 윤석열 정부가 국정과제로 내년 처음 시행하는 '부모급여(만 0·1세 각각 100만 원, 50만 원, 내년에는 각각 70만 원, 35만 원 지급)'를 위해 보육 관련 사업 예산을 대부분 삭감한 것으로 해석한다. 복지부는 영아수당을 확대한 부모급여 사업에 약 1조6,000억 원을 책정했다. 지난해(영아수당)보다 366% 증액된 규모다."
        )

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



