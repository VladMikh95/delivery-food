package ml.vladmikh.projects.delivery_food.data.network.models

import ml.vladmikh.projects.delivery_food.data.local.entities.DishLocalDataSource

data class DishRemoteDataSource (

    val description: String = "",
    val id: Int = 0,
    val image_url: String = "",
    val name: String ="",
    val price: Int = 0,
    val tegs: List<String> = ArrayList<String>(),
    val weight: Int = 0
)

fun DishRemoteDataSource.asEntityModel() = DishLocalDataSource(
    id = id,
    description = description,
    imageUrl = image_url,
    name = name,
    price = price,
    tags  = tegs,
    weight = weight
)

