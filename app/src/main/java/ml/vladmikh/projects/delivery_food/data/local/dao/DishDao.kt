package ml.vladmikh.projects.delivery_food.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ml.vladmikh.projects.delivery_food.data.local.entities.DishLocalDataSource


@Dao
interface DishDao {

    @Query("SELECT * from dish_table")
    fun getAllDishLocalDataSource(): Flow<List<DishLocalDataSource>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDishesLocalDataSource(dishesLocalDataSource: List<DishLocalDataSource>)
}