package ml.vladmikh.projects.delivery_food.ui.dishes

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            onItemClicked(getItem(position))
        }
        holder.bind(getItem(position))
    }

    class TagViewHolder(private var binding: TagItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(tag: Tag) {

            binding.textViewTag.text = tag.name

            if (tag.isSelected) {
                binding.cardView.setCardBackgroundColor(Color.parseColor("#32FD3A69"))
                binding.textViewTag.setTextColor(Color.parseColor("#FFFD3A69"))
            } else {
                binding.cardView.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"))
                binding.textViewTag.setTextColor(Color.parseColor("#FFC3C4C9"))
            }
        }
    }
}