package com.example.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.storyapp.data.AllStoryResponse
import com.example.storyapp.databinding.ActivityDetailBinding
import com.example.storyapp.utils.Constant

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var story: AllStoryResponse.ListStory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Story"
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        story = intent.getParcelableExtra(Constant.BUNDLE_STORY)!!
        setData()
    }

    private fun setData() {
        binding.tvUserNameDetail.text = story.name
        binding.tvCaptionDetail.text = story.description

        Glide.with(this)
            .load(story.photoUrl)
            .centerCrop()
            .skipMemoryCache(true)
            .into(binding.imgPostDetail)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}