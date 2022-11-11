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
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.*
import com.example.gukrule.PageUrl.pageUrl
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

//국회행정지원 조사처 기본경비
//국회행정지원 예산처 기본경비
//국회행정지원 도서관 기본경비
//국회행정지원 사무처 기본경비
//국회행정지원 조사처 인건비
//국회행정지원 예산처 인건비
//국회행정지원 도서관 인건비
//국회행정지원 사무처 인건비
//의정활동지원 의정지원
//의정활동지원 예비금
//의정활동지원 국회활동관련단체지원
//의정활동지원 의회외교
//의정활동지원 위원회운영지원
//입법조사처운영 입법조사처 정보화
//입법조사처운영 입법및정책조사분석
//입법조사처운영 기획관리및운영지원
//예산정책처운영 예산정책처정보화
//예산정책처운영 국가재정경제분석및의안비용추계
//예산정책처운영 기획관리및정책총괄지원
//국회도서관운영 전자도서관운영
//국회도서관운영 도서관자료확충및보존
//ㅜ회도서관운영 입법정보지원
//국회도서관운영 도서관운영지원
//국회사무처운영 국회방송운영
//국회사무처운영 의회운영교육 수입대체경비
//국회사무처운영 입법정보화
//국회사무처운영 국회청사확보및시설관리
//국회사무처운영 법제활동지원
//국회사무처운영 의회운영지원

private var word: String? = null

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val keywordData = mutableListOf<Keyword>()
    private var articleList = listOf<List<String>>()
    private lateinit var mainActivity : MainActivity
//    lateinit var keywordsAdapter: KeywordsAdapter
//    lateinit var articlesAdapter: ArticlesAdapter
    private var pageInt: Int = 1
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    // onCreateView
    @SuppressLint("NotifyDataSetChanged")
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
        keywordRecyclerView.addItemDecoration(KeywordItemDecorator(40))

        val articleRecyclerView: RecyclerView = binding.articleRecycler
        articleRecyclerView.adapter = articlesAdapter
        articleRecyclerView.addItemDecoration(ArticleItemDecorator(1, Color.GRAY))


        // userIdx = getUserIdx()
        val requestData = CrawlingRequestData(userIdx = 218, keyword = word, page = pageInt)
        var articleApi: RetrofitClient.CrawlingNewsApi =
            RetrofitClient.initLocalRetrofit().create(RetrofitClient.CrawlingNewsApi::class.java)
        articleApi.getCrawlingNews(
            // jwtKey = getUserToken()
            jwtKey = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyMTgsImlhdCI6MTY2Nzk4OTQ1MCwiZXhwIjoxNjY5NDYwNjc5fQ.iYa-I-ExdJoF6LSJ_zlPXB4d49lK_RitfeWhNQnKTDE",
            crawlingRequestData = requestData,
        )
            .enqueue(object : Callback<CrawlingNewList> {
                override fun onResponse(
                    call: Call<CrawlingNewList>,
                    response: Response<CrawlingNewList>,
                ) {
                    val pCode = response.body()!!.code
                    var errMessage: String? = "에러메시지"
                    if (pCode == 1000) {
                        Log.d("success", response.body()!!.code.toString())
                        Log.d("success", response.body()!!.message.toString())
                        Log.d("success", response.body()!!.result!!.toString())
                        articleList = response.body()!!.result!!

                        val articleData = mutableListOf<Article>()
                        for (i: Int in 0..9) {
                            articleData.add(
                                Article(
                                    id = i + 1,
                                    budgetKey = word,
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
                            2001 -> errMessage = pCode.toString() + "JWT를 입력해주세요"
                            2002 -> errMessage = pCode.toString() + "유효하지 않은 JWT입니다."
                            2003 -> errMessage = pCode.toString() + "권한이 없는 유저의 접근"
                            2044 -> errMessage = pCode.toString() + "page를 입력해주세요"
                            4000 -> errMessage = pCode.toString() + "데이터베이스 연결에 실패하였습니다."
                        }
                        Toast.makeText(
                            view?.context,
                            "${pCode}\n${errMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CrawlingNewList>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                }
            })


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
            keywordsAdapter.notifyDataSetChanged()
        }

        return root
    }


    fun loadPost(x: String?, y: Int) {
            val articlesAdapter = ArticlesAdapter()
            // userIdx = getUserIdx()
            val requestData = CrawlingRequestData(userIdx = 218, keyword = x, page = y)
            var articleApi: RetrofitClient.CrawlingNewsApi = RetrofitClient.initLocalRetrofit()
                .create(RetrofitClient.CrawlingNewsApi::class.java)
            articleApi.getCrawlingNews(
                // jwtKey = getUserToken()
                jwtKey = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWR4IjoyMTgsImlhdCI6MTY2Nzk4OTQ1MCwiZXhwIjoxNjY5NDYwNjc5fQ.iYa-I-ExdJoF6LSJ_zlPXB4d49lK_RitfeWhNQnKTDE",
                crawlingRequestData = requestData,
            )
                .enqueue(object : Callback<CrawlingNewList> {
                    override fun onResponse(
                        call: Call<CrawlingNewList>,
                        response: Response<CrawlingNewList>,
                    ) {
                        val pCode = response.body()!!.code
                        var errMessage: String? = "에러메시지"
                        if (pCode == 1000) {
                            Log.d("success", response.body()!!.code.toString())
                            Log.d("success", response.body()!!.message.toString())
                            Log.d("success", response.body()!!.result!!.toString())
                            articleList = response.body()!!.result!!

                            val articleData = mutableListOf<Article>()
                            for (i: Int in 0..9) {
                                articleData.add(
                                    Article(
                                        id = i + 1,
                                        budgetKey = word,
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
                                2001 -> errMessage = pCode.toString() + "JWT를 입력해주세요"
                                2002 -> errMessage = pCode.toString() + "유효하지 않은 JWT입니다."
                                2003 -> errMessage = pCode.toString() + "권한이 없는 유저의 접근"
                                2044 -> errMessage = pCode.toString() + "page를 입력해주세요"
                                4000 -> errMessage = pCode.toString() + "데이터베이스 연결에 실패하였습니다."
                            }
                            Toast.makeText(
                                view?.context,
                                "${pCode}\n${errMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
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

