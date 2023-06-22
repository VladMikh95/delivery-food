package ml.vladmikh.projects.delivery_food.ui.dishes

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ml.vladmikh.projects.delivery_food.R
import ml.vladmikh.projects.delivery_food.databinding.TagItemBinding

class TagAdapter(private val onItemClicked: (Tag) -> Unit) : ListAdapter<Tag, TagAdapter.TagViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Tag>() {

            override fun areItemsTheSame(oldTag: Tag, newTag: Tag): Boolean {
                return oldTag == newTag
            }

            override fun areContentsTheSame(oldTag: Tag, newTag: Tag): Boolean {
                return oldTag.isSelected == newTag.isSelected
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val viewHolder = TagViewHolder(
            TagItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )

        return viewHolder
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClicked(getItem(position))
        }

        holder.bind(getItem(position))
    }

    class TagViewHolder(private var binding: TagItemBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(tag: Tag) {

            binding.textViewTag.text = tag.name

            if (tag.isSelected) {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#32FD3A69"))
                binding.textViewTag.setTextColor(Color.parseColor("#FFFD3A69"))
            } else {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                binding.textViewTag.setTextColor(Color.parseColor("#FFC3C4C9"))
            }
        }
    }
}