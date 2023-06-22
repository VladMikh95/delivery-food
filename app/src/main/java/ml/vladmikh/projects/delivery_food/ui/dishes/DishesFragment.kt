package ml.vladmikh.projects.delivery_food.ui.dishes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ml.vladmikh.projects.delivery_food.databinding.FragmentDishesBinding



@AndroidEntryPoint
class DishesFragment : Fragment() {

    private lateinit var binding: FragmentDishesBinding
    private val viewModel by viewModels<DishesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDishesBinding.inflate(inflater, container, false)
        val dishAdapter = DishAdapter()
        binding.recyclerViewDishes.adapter = dishAdapter

        val tagAdapter = TagAdapter {tag -> viewModel.sortDishesByTag(tag)}
        binding.recyclerViewTags.adapter = tagAdapter

        viewModel.getDishes()

        viewModel.dishesList.observe(this.viewLifecycleOwner) { dishes ->

            dishAdapter.submitList(dishes)
        }

        viewModel.tagsList.observe(this.viewLifecycleOwner) { tags ->

            tagAdapter.submitList(tags)
        }

        viewModel.status.observe(viewLifecycleOwner) {newStatus ->
            when(newStatus) {
                DishDataStatus.SUCCESS -> binding.imageViewError.visibility = View.GONE
                DishDataStatus.LOADING -> binding.imageViewError.visibility = View.GONE
                DishDataStatus.ERROR -> binding.imageViewError.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}