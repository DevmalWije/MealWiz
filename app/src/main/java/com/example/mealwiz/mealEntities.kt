package com.example.mealwiz

import androidx.room.*

// MealObject class
@Entity (tableName = "Meals_Table")
data class MealEntity(
    @PrimaryKey val mealId: Int,
    @ColumnInfo(name="Meal_Name") val mealName:String?,
    @ColumnInfo(name = "Thumb_URL") val mealImage:String?,
    @ColumnInfo(name = "Drink") val drinkAlt:String?,
    @ColumnInfo(name = "Diet") val mealDiet:String?,
    @ColumnInfo(name = "Meal_Origin") val mealOrigin:String?,
    @ColumnInfo(name = "Meal_Tags") val mealTags:String?,
    @ColumnInfo(name = "Meal_Instructions") val mealInstructions:String?,
    @ColumnInfo(name = "Meal_Video") val mealYoutube:String?,
    @ColumnInfo(name = "Image_Source") val ImageSource:String?,
    @ColumnInfo(name = "Creative_Commons") val CreativeCommonsConfirmed:String?,
    @ColumnInfo(name = "Date_Modified") val dateModified:String?,
)

// IngredientObject class
@Entity(tableName = "Ingredients_Table")
data class IngredientEntity(
    @PrimaryKey (autoGenerate = true) val ingredientId: Int=0,
    @ColumnInfo(name="Meal_ID") val mealId: Int,
    @ColumnInfo (name="Ingredient_Name") val ingredientName: String?,
    @ColumnInfo (name="Ingredient_Amount") val ingredientMeasure: String?
)

////connecting above two tables
//data class MealWithIngredients(
//    @Embedded val meal: MealEntity,
//    @Relation(
//        parentColumn = "mealId",
//        entityColumn = "Meal_ID"
//    )
//    val ingredients: List<IngredientEntity>
//)

//setting mealId as a foreign key in Ingredients_Table
data class MealWithIngredients(
    @Embedded val meal: MealEntity,
    @Relation(
        parentColumn = "mealId",
        entityColumn = "Meal_ID",
        entity = IngredientEntity::class
    )
    val ingredients: List<IngredientEntity>
)

