package com.nigam.articles

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nigam.articles.api.AppResult
import com.nigam.articles.databinding.ActivityMainBinding
import com.nigam.articles.model.Blog
import com.nigam.articles.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModels: MainViewModel by viewModels()

    private val data by lazy {
        mutableListOf<Blog>()
    }

    private val adapter by lazy {
        BlogAdapter(this, data)
    }

    private var page: Int = 1
    private var isLastPage = false
    private var isLoading = false

    companion object {
        const val PAGE_SIZE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        viewModels.liveData.observe(this, {
            binding.progressbar.visibility = View.GONE
            when (it) {
                is AppResult.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                    isLoading = true
                }
                is AppResult.Success -> {
                    val blogList = it.output as List<Blog>
                    data.addAll(blogList)
                    adapter.notifyItemRangeInserted(adapter.itemCount - 1, blogList.size)
                    isLastPage = blogList.isEmpty()
                    isLoading = false
                }
            }
        })
        viewModels.getBlogs(page)
        binding.rvArticles.adapter = adapter

        binding.rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE
                    ) {
                        page++
                        viewModels.getBlogs(page)
                    }
                }
            }
        })
    }
}