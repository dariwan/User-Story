package com.example.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.data.AllStoryResponse
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.utils.Constant
import com.example.storyapp.view.detail.DetailActivity

class StoryAdapter :
    PagingDataAdapter<AllStoryResponse.ListStory, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null){
            holder.bind(data)
        }
    }

     class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: AllStoryResponse.ListStory) {
            binding.tvUserName.text = "Nama : ${story.name}"
            binding.tvCaption.text = "Desc : ${story.description}"
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .centerCrop()
                .skipMemoryCache(true)
                .into(binding.imgPost)

            itemView.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(Constant.BUNDLE_STORY, story)

                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imgPost, "photo"),
                    Pair(binding.tvUserName, "title"),
                    Pair(binding.tvCaption, "description"),
                )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }


    companion object {
         val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AllStoryResponse.ListStory>() {
            override fun areItemsTheSame(oldItem: AllStoryResponse.ListStory, newItem: AllStoryResponse.ListStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AllStoryResponse.ListStory, newItem: AllStoryResponse.ListStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}