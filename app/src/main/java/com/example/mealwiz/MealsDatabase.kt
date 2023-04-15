package com.example.mealwiz

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MealEntity::class, IngredientEntity::class], version = 1)
abstract class MealsDatabase: RoomDatabase() {
    abstract fun mealsDao(): MealsDao

    companion object {
        private var instance: MealsDatabase? = null

        fun getInstance(context: Context): MealsDatabase {
            return instance ?: synchronized(MealsDatabase::class) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java, "MealsDatabase"
                )
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
    }
}