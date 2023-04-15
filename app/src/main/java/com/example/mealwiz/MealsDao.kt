package com.example.mealwiz

import androidx.room.*

@Dao
interface MealsDao {
    @Query("SELECT * FROM Meals_Table")
    fun getAllMeals(): List<MealEntity>

    @Query("SELECT * FROM Ingredients_Table WHERE ingredient_Name LIKE :meal_ID")
    fun getMealByIngredients(meal_ID: Int): IngredientEntity

    //checking both meal and ingredient tables matching string and returning meal regardless of string case
    @Query("SELECT Meals_Table.*, GROUP_CONCAT(Ingredients_Table.Ingredient_Name || ' - ' || Ingredients_Table.Ingredient_Amount, '\n') AS ingredientsList " +
            "FROM Meals_Table " +
            "LEFT JOIN Ingredients_Table ON Meals_Table.mealId = Ingredients_Table.Meal_ID " +
            "WHERE Meals_Table.Meal_Name LIKE '%' || :searchQuery || '%' OR Ingredients_Table.Ingredient_Name LIKE '%' || :searchQuery || '%' " +
            "GROUP BY Meals_Table.mealId")
    fun searchForMealsWithIngredients(searchQuery: String): List<MealWithIngredients>

    //checking both meal and ingredient tables matching string and returning list of mealId's regardless of string case
    @Query("SELECT Meals_Table.mealId " +
            "FROM Meals_Table " +
            "LEFT JOIN Ingredients_Table ON Meals_Table.mealId = Ingredients_Table.Meal_ID " +
            "WHERE Meals_Table.Meal_Name LIKE '%' || :searchQuery || '%' OR Ingredients_Table.Ingredient_Name LIKE '%' || :searchQuery || '%' " +
            "GROUP BY Meals_Table.mealId")
    fun searchForMealIds(searchQuery: String): List<Int>

    //inserting meal into their respective tables
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: MealEntity): Long

    //inserting list of ingredients into their respective tables
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredient(ingredient: IngredientEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: List<IngredientEntity>)
}