package com.methe.newsapptest.data.remote

import com.methe.newsapptest.data.remote.models.NewsAllResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("everything?apiKey=316db5b47561429dbff0122a3010ab69")
    suspend fun getEverythingNews(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("q") query: String
    ) : NewsAllResponse

}