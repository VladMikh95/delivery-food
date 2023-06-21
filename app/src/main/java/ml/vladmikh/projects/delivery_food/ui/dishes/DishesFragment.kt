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

        viewModel.dishesList.observe(this.viewLifecycleOwner) { items ->

            items.let {
                dishAdapter.submitList(it)
                tagAdapter.submitList(viewModel.tagsList.value)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {newStatus ->

        }

        return binding.root
    }
}