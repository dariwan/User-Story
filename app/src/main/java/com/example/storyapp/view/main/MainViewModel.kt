package com.example.storyapp.view.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.storyapp.data.AllStoryResponse
import com.example.storyapp.injection.Injection
import com.example.storyapp.repository.StoryRepository


class MainViewModel(storyRepository: StoryRepository) : ViewModel() {

    val story: LiveData<PagingData<AllStoryResponse.ListStory>> =
        storyRepository.getStoryList().cachedIn(viewModelScope)


    class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)){
                return MainViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}