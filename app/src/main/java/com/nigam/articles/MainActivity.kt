package com.nigam.articles

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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

    val page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        viewModels.liveData.observe(this, Observer {
            binding.progressbar.visibility = View.GONE
            when (it) {
                is AppResult.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is AppResult.Success -> {
                    val blogList = it.output as List<Blog>
                    data.addAll(blogList)
                    adapter.notifyItemRangeInserted(adapter.itemCount - 1, blogList.size)
                }
            }
        })
        viewModels.getBlogs(page)
        binding.rvArticles.adapter = adapter
    }
}