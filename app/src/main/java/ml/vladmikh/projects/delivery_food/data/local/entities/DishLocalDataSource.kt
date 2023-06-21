package ml.vladmikh.projects.delivery_food.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ml.vladmikh.projects.delivery_food.data.model.Dish

@Entity(tableName = "dish_table")
data class DishLocalDataSource (

    @PrimaryKey val id: Int,
    val description: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    val name: String,
    val price: Int,
    val tags: List<String>,
    val weight: Int,
)

fun DishLocalDataSource.asExternalModel() = Dish(
    id = id,
    description = description,
    imageUrl = imageUrl,
    name = name,
    price = price,
    tags  = tags,
    weight = weight
)