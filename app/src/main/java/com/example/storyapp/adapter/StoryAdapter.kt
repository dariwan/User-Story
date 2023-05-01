import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyapp.data.AllStoryResponse
import com.example.storyapp.databinding.ItemStoryBinding
import com.example.storyapp.utils.Constant
import com.example.storyapp.view.detail.DetailActivity

class StoryAdapter(
    private val context: Context,
    private val storyList: ArrayList<AllStoryResponse.ListStory>
) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryAdapter.StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryAdapter.StoryViewHolder, position: Int) {
        storyList[position].let { story ->
            holder.bind(story)
        }
    }

    override fun getItemCount(): Int = storyList.size

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: AllStoryResponse.ListStory) {
            with(binding) {
                val name = story.name
                tvUserName.text = buildString {
                    append("Nama: ")
                    append(name)
                }

                val desc = story.description
                tvCaption.text = buildString {
                    append("Desc: ")
                    append(desc)
                }



                Glide.with(context)
                    .load(story.photoUrl)
                    .centerCrop()
                    .skipMemoryCache(true)
                    .into(imgPost)
            }

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

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<AllStoryResponse.ListStory>) {
        storyList.clear()
        storyList.addAll(data)
        notifyDataSetChanged()
    }

}