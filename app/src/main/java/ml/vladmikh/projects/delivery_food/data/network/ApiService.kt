package ml.vladmikh.projects.delivery_food.data.network

import ml.vladmikh.projects.delivery_food.data.network.models.DishesRemoteDataSource
import retrofit2.http.GET

interface ApiService {

    @GET("v3/c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishesRemoteDataSource(): DishesRemoteDataSource

}