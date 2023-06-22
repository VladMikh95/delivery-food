package ml.vladmikh.projects.delivery_food.ui.dishes

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ml.vladmikh.projects.delivery_food.R
import ml.vladmikh.projects.delivery_food.data.model.Dish
import ml.vladmikh.projects.delivery_food.databinding.DishItemBinding

class DishAdapter() : ListAdapter<Dish, DishAdapter.DishViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Dish>() {
            override fun areItemsTheSame(oldDish: Dish, newDish: Dish): Boolean {
                return oldDish == newDish
            }

            override fun areContentsTheSame(oldDish: Dish, newDish: Dish): Boolean {
                return (oldDish.id == newDish.id
                        || oldDish.name == newDish.name
                        || oldDish.description == newDish.description
                        || oldDish.price == newDish.price
                        || oldDish.imageUrl == newDish.imageUrl
                        || oldDish.tags == newDish.tags
                        || oldDish.weight == newDish.weight)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val viewHolder = DishViewHolder(
            DishItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        return viewHolder
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {

        holder.bind(getItem(position))
        holder.button.text = holder.itemView.context.getString(R.string.dish_item_button, getItem(position).price.toString())
    }

    class DishViewHolder(private var binding: DishItemBinding): RecyclerView.ViewHolder(binding.root) {

        val button = binding.buttonPrice

        fun bind(dish: Dish) {

            binding.textViewTitle.text = dish.name
            binding.imageViewDish.load(dish.imageUrl.toUri().buildUpon().scheme("https").build())
            binding.textViewDescription.text = dish.description

        }
    }
}