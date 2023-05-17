package com.example.utils

import com.example.storyapp.data.AllStoryResponse

object DataDummy {
    fun generateResponseDummyStory(): List<AllStoryResponse.ListStory>{
        val newList : MutableList<AllStoryResponse.ListStory> = arrayListOf()
        for (i in 0..10){
            val storyList = AllStoryResponse.ListStory(
                "FvU4u0Vp222PMsFg",
                "Dimas Kahfi",
                "Lorem Ipsum",
                "https://story-api.dicoding.dev/images/stories/photos-1111123658595_dummy-pic.png",
                "2024-09-08T06:22:22.598Z,",
                -10.111,
                -16.222
            )
            newList.add(storyList)
        }
       return newList
    }
}