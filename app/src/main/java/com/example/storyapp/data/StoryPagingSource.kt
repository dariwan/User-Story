package com.example.storyapp.data


import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.apihelper.ApiService
import kotlin.collections.*
import com.example.storyapp.utils.SesionManager

class StoryPagingSource(
    private val apiService: ApiService,
    private val context: Context
    ) : PagingSource<Int, AllStoryResponse.ListStory>() {
    override fun getRefreshKey(state: PagingState<Int, AllStoryResponse.ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AllStoryResponse.ListStory> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.allStory("Bearer ${SesionManager(context).getToken().token}",position, params.loadSize).listStory.toList()

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (responseData.isNullOrEmpty()) null else position +1
            )
        } catch (e : Exception){
            return LoadResult.Error(e)
        }
    }


    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}