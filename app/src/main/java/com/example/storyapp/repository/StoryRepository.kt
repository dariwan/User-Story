package com.example.storyapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.apihelper.ApiService
import com.example.storyapp.data.AllStoryResponse
import com.example.storyapp.data.StoryPagingSource
import com.example.storyapp.utils.SesionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(private val apiService: ApiService, private val context: Context) {

    private val getStoryLocation = MediatorLiveData<com.example.storyapp.utils.Result<List<AllStoryResponse.ListStory>>>()

     fun getStoryList(): LiveData<PagingData<AllStoryResponse.ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, context)
            }
        ).liveData
    }

    fun getLocationStory(): LiveData<com.example.storyapp.utils.Result<List<AllStoryResponse.ListStory>>>{
        val client = apiService.storyWithLocation("Bearer ${SesionManager(context).getToken().token}", 1)
        client.enqueue(object : Callback<AllStoryResponse>{
            override fun onResponse(
                call: Call<AllStoryResponse>,
                response: Response<AllStoryResponse>
            ) {
                if (response.isSuccessful){
                    val storiesLocation = response.body()?.listStory
                    if (storiesLocation != null){
                        getStoryLocation.value = com.example.storyapp.utils.Result.Success(storiesLocation)
                    }
                } else{
                    getStoryLocation.value = com.example.storyapp.utils.Result.Error("Failed To Get Location")
                }
            }

            override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                getStoryLocation.value = com.example.storyapp.utils.Result.Error("Failed To Get Location")
            }

        })
        return getStoryLocation
    }


}