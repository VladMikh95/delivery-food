package ml.vladmikh.projects.delivery_food.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ml.vladmikh.projects.delivery_food.data.local.DeliveryFoodDatabase
import ml.vladmikh.projects.delivery_food.data.local.dao.DishDao
import ml.vladmikh.projects.delivery_food.data.network.ApiService
import ml.vladmikh.projects.delivery_food.data.repository.DishRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String = "https://run.mocky.io/"


    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesDishOrderDao(database: DeliveryFoodDatabase): DishDao {
        return database.dishDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DeliveryFoodDatabase {
        return Room.databaseBuilder(
            context,
            DeliveryFoodDatabase::class.java,
            "delivery_food_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDishRepository(dishDao: DishDao, mainService: ApiService): DishRepository = DishRepository(dishDao,mainService)

}