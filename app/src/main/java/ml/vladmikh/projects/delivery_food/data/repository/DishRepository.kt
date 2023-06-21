package ml.vladmikh.projects.delivery_food.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ml.vladmikh.projects.delivery_food.data.local.dao.DishDao
import ml.vladmikh.projects.delivery_food.data.local.entities.DishLocalDataSource
import ml.vladmikh.projects.delivery_food.data.local.entities.asExternalModel
import ml.vladmikh.projects.delivery_food.data.model.Dish
import ml.vladmikh.projects.delivery_food.data.network.ApiService
import ml.vladmikh.projects.delivery_food.data.network.models.asEntityModel
import javax.inject.Inject

class DishRepository @Inject constructor(
    private val dishDao: DishDao,
    private val apiService: ApiService
)  {

    private var isRefreshed = false

    fun getDishStream(): Flow<List<Dish>> {

        return dishDao.getAllDishLocalDataSource().map { dishLocalDataSource ->
            dishLocalDataSource.map(DishLocalDataSource::asExternalModel)
        }.onEach {
            //Обновление данных из интернета при запуске приложения
            if (!isRefreshed) {
                refreshDishes()
                isRefreshed = true
            }
        }
    }

    suspend fun refreshDishes() {

        apiService.getDishesRemoteDataSource()
            .also { externalDishes ->
                if (externalDishes.dishes.isNotEmpty()) {
                    dishDao.insertDishesLocalDataSource(
                        externalDishes.dishes.map { it.asEntityModel() }
                    )
                }
            }
    }
}
