package com.nigam.articles.api

import com.nigam.articles.model.Blog
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiService {
    @GET("blogs")
    suspend fun blogs(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): List<Blog>
}