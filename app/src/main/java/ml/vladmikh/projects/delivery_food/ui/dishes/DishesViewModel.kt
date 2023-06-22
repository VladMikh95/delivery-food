package ml.vladmikh.projects.delivery_food.ui.dishes

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ml.vladmikh.projects.delivery_food.data.model.Dish
import ml.vladmikh.projects.delivery_food.data.model.asResult
import ml.vladmikh.projects.delivery_food.data.model.Result
import ml.vladmikh.projects.delivery_food.data.repository.DishRepository
import javax.inject.Inject

@HiltViewModel
class DishesViewModel @Inject constructor(
    private val repository: DishRepository
) : ViewModel(){

    private lateinit var listDishesStream: List<Dish>

    private var _dishesList = MutableLiveData<List<Dish>>()
    val dishesList: LiveData<List<Dish>> get() = _dishesList

    private var _tagsList = MutableLiveData<MutableList<Tag>>()
    val tagsList: LiveData<MutableList<Tag>> get() = _tagsList

    private var _status = MutableLiveData<DishDataStatus>()

    val status: LiveData<DishDataStatus> get() = _status

    fun getDishes() {

        viewModelScope.launch {
            repository.getDishStream().asResult().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _status.value = DishDataStatus.SUCCESS
                        listDishesStream = result.data
                        _dishesList.value = listDishesStream
                        createTagList()
                    }
                    is Result.Loading -> _status.value = DishDataStatus.LOADING
                    is Result.Error -> _status.value = DishDataStatus.ERROR
                }
            }
        }
    }


    //создание списка тегов
    private fun createTagList() {
        val listTags = ArrayList<Tag>()
        val listTagNames = getTagsFromDish()

        if (listTagNames.isNotEmpty()) {
            for (tagName in listTagNames) {
                listTags.add(Tag(tagName, false))
            }
            //Первый тег по умолчанию выбран
            listTags[0].isSelected = true

            _tagsList.value = listTags
        }

    }

    //Получение имен тегов из объектов Dish
    private fun getTagsFromDish(): List<String> {

        val listTagNames = ArrayList<String>()

        for (dish in listDishesStream) {

            val tagNames = dish.tags

            for (tagName in tagNames) {
                if (!isHasTags(tagName, listTagNames)) {
                    listTagNames.add(tagName)
                }
            }
        }

        return listTagNames

    }

    //Проверка есть ли в списке тегов, тег передоваемый в качестве параметра
    private fun isHasTags (tagName: String, listTags: List<String>): Boolean {

        for (tag in listTags) {

            if (tagName == tag) {
                return true
            }
        }
        return  false
    }

    //Изменяет список блюд dishesList в зависимости от тега
    fun sortDishesByTag(tag: Tag) {
        val dishes = ArrayList<Dish>()

        for (dish in listDishesStream) {

            if (isHasTags(tag.name, dish.tags)) {
                dishes.add(dish)
            }
        }

        _dishesList.value = dishes
        changeSelectedTag(tag.name)
    }

    //Изменение выбранного тега в списке
    fun changeSelectedTag(tagName : String) {
        val tags = _tagsList.value
        val newListTag = ArrayList<Tag>()

        if (tags != null) {
            for(tag in tags) {

                if (tag.name == tagName) {
                    newListTag.add(Tag(tag.name, true))
                } else {
                    newListTag.add(Tag(tag.name, false))
                }
            }

            /*for (i in 0 until _tagsList.value.size) {
                if (newListTag[i].name == tagName) {
                    newListTag[i] = newListTag[i].copy(isSelected = true)
                } else {
                    newListTag[i].isSelected = false
                }
            }*/

        }

        _tagsList.value = newListTag

    }
}