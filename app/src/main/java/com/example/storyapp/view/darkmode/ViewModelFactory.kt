package com.example.storyapp.view.darkmode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val pref: SettingPreferences): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DarkModeViewModel::class.java)){
            return DarkModeViewModel(pref) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class" + modelClass.name)
    }
}