package com.nigam.articles.api


import com.nigam.articles.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitService {

    private var logging = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level =
                if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
        }
    }

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://5e99a9b1bc561b0016af3540.mockapi.io/jet2/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}