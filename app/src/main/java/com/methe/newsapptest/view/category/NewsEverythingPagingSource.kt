package com.methe.newsapptest.view.category

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.methe.newsapptest.data.remote.NewsApi
import com.methe.newsapptest.data.remote.models.ArticlesItem
import javax.inject.Inject

class NewsEverythingPagingSource @Inject constructor(
    private val newsApi: NewsApi,
    private val query: String
    ) : PagingSource<Int, ArticlesItem>() {

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val position = params.key ?: 1
            val pageSize = 5
            val response = newsApi.getEverythingNews(position, pageSize, query)

            return LoadResult.Page(
                data = response.articles ?: arrayListOf(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.totalResults) null else position + 1
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

}