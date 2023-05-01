package com.example.storyapp.view.main

import StoryAdapter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.apihelper.ApiConfig
import com.example.storyapp.data.AllStoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _allStory = MutableLiveData<ArrayList<AllStoryResponse.ListStory>>()
    val allStory: LiveData<ArrayList<AllStoryResponse.ListStory>> = _allStory

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getAllStory(token: String) {
        val apiService = ApiConfig.getApiService().allStory(token)
        apiService.enqueue(object : Callback<AllStoryResponse> {
            override fun onResponse(
                call: Call<AllStoryResponse>,
                response: Response<AllStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _allStory.value = responseBody.listStory
                    }else {
                        _errorMessage.value = "Gagal Mengambil data cerita"
                    }
                }
            }

            override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: " + t.message)
            }
        })
    }


    companion object {
        private const val TAG = "MainViewModel"
    }

}