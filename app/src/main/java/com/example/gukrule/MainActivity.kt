package com.example.gukrule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gukrule.databinding.ActivityMainBinding
import com.example.gukrule.keyword.data.Keyword
import com.example.gukrule.adapter.KeywordsAdapter
import com.example.gukrule.databinding.FragmentFeedBinding
import com.example.gukrule.keyword.HorizontalItemDecorator
import com.example.gukrule.ui.feed.FeedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var keywordsAdapter: KeywordsAdapter
    val keywordDatas = mutableListOf<Keyword>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.main_toolbar))
        supportActionBar?.apply {
            setIcon(R.drawable.tmp_resize)
            setDisplayUseLogoEnabled(true)
            setTitle(R.string.app_name)
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        initFeedRecycler()
    }

    private fun initFeedRecycler() {

        /* top_recycler 참조 방법을 찾는 중입니다... 기능 구현 확인용 */
        setContentView(FragmentFeedBinding.inflate(layoutInflater).root)
        keywordsAdapter = KeywordsAdapter(this)

        val topRecyclerView: RecyclerView = findViewById(R.id.top_recycler)
        topRecyclerView.adapter = keywordsAdapter
        topRecyclerView.addItemDecoration(HorizontalItemDecorator(20))

        // 더미 데이터
        keywordDatas.apply {
            add(
                Keyword(
                    id = 1,
                    word = resources.getString(R.string.keyword1_word),
                ),
            )
            add(
                Keyword(
                    id = 2,
                    word = resources.getString(R.string.keyword2_word),
                ),
            )
            add(
                Keyword(
                    id = 3,
                    word = resources.getString(R.string.keyword3_word),
                ),
            )
            add(
                Keyword(
                    id = 4,
                    word = resources.getString(R.string.keyword4_word),
                ),
            )
            add(
                Keyword(
                    id = 5,
                    word = resources.getString(R.string.keyword5_word),
                ),
            )

            keywordsAdapter.keywordDatas = keywordDatas
            keywordsAdapter.notifyDataSetChanged()

//            setContentView(binding.root)

        }
    }
}