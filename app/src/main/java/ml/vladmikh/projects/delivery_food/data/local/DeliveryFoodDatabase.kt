package ml.vladmikh.projects.delivery_food.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ml.vladmikh.projects.delivery_food.data.local.dao.DishDao
import ml.vladmikh.projects.delivery_food.data.local.entities.DishLocalDataSource

@Database(entities = [DishLocalDataSource::class], version = 1, exportSchema = false)
@TypeConverters(ArrayListConverter::class)
abstract class DeliveryFoodDatabase: RoomDatabase() {

    abstract fun dishDao(): DishDao
}