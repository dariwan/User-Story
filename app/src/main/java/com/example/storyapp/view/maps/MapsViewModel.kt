package com.example.storyapp.view.maps

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.injection.Injection
import com.example.storyapp.repository.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStoryWithLocation() = storyRepository.getLocationStory()

    class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)){
                return MapsViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}