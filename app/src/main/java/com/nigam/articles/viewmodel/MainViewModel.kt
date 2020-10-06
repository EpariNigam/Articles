package com.nigam.articles.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nigam.articles.api.AppResult
import com.nigam.articles.api.IApiService
import com.nigam.articles.api.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val apiService = RetrofitService.createService(IApiService::class.java)
    var liveData: MutableLiveData<AppResult<Any>> = MutableLiveData<AppResult<Any>>()

    fun getBlogs(page: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                liveData.value = AppResult.Loading
            }
            val response = apiService.blogs(page)
            withContext(Dispatchers.Main) {
                liveData.value = AppResult.Success(response)
            }
        }
    }
}