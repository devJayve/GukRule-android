package com.example.gukrule.ui.feed

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.MainActivity
import com.example.gukrule.NewsActivity
import com.example.gukrule.R
import com.example.gukrule.adapter.ArticlesAdapter
import com.example.gukrule.adapter.KeywordsAdapter
import com.example.gukrule.article.Article
import com.example.gukrule.article.ArticleItemDecorator
import com.example.gukrule.databinding.FragmentFeedBinding
import com.example.gukrule.keyword.Keyword
import com.example.gukrule.keyword.KeywordItemDecorator


class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val keywordData = mutableListOf<Keyword>()
    private val articleData = mutableListOf<Article>()
    lateinit var keywordsAdapter: KeywordsAdapter
    lateinit var articlesAdapter: ArticlesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val keywordsAdapter = KeywordsAdapter()
        val articlesAdapter = ArticlesAdapter()

        val keywordRecyclerView: RecyclerView = binding.keywordRecycler
        keywordRecyclerView.adapter = keywordsAdapter
        keywordRecyclerView.addItemDecoration(KeywordItemDecorator(30))

        val articleRecyclerView: RecyclerView = binding.articleRecycler
        articleRecyclerView.adapter = articlesAdapter
        articleRecyclerView.addItemDecoration(ArticleItemDecorator(1, Color.GRAY))

        val keywordArrow: ImageButton = binding.keywordArrow
        val articleArrow: ImageButton = binding.articleArrow

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
        articleData.apply {
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
                    id = 7,
                    budgetKey = "사무처 인건비",
                    title = "6년째 공회전 북한인권재단... 텅 빈 사무실 임대료만 24억",
                    image = R.drawable.article2,
                    content = "이사회 구성 지연으로 출범 못 한 북한인권재단에 예산 24억 들어... 통일부, 마포구 700평 규모 사무실 임차했지만 23개월만에 철수",
                    date = "2022-10-11 09:00"
                ),
            )
            add(
                Article(
                    id = 8,
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
                    id = 9,
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
                    id = 10,
                    budgetKey = "사무처 인건비",
                    title = "6년째 공회전 북한인권재단... 텅 빈 사무실 임대료만 24억",
                    image = R.drawable.article2,
                    content = "이사회 구성 지연으로 출범 못 한 북한인권재단에 예산 24억 들어... 통일부, 마포구 700평 규모 사무실 임차했지만 23개월만에 철수",
                    date = "2022-10-11 09:00"
                ),
            )

            articlesAdapter.articleData = articleData
            articlesAdapter.notifyDataSetChanged()
        }

        // Fragment to Activity
        keywordArrow.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(this, NewsActivity::class.java))
                finish()
            }
        }
        articleArrow.setOnClickListener{
            requireActivity().run{
                startActivity(Intent(this, NewsActivity::class.java))
                finish()
            }
        }

        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}