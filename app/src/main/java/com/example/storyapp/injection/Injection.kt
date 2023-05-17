package com.example.storyapp.injection

import android.content.Context
import com.example.storyapp.apihelper.ApiConfig
import com.example.storyapp.repository.StoryRepository

object Injection {
    fun provideRepository( context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository(apiService, context)
    }
}