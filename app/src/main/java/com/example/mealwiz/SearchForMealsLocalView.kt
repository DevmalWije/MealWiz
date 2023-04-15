package com.example.mealwiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchForMealsLocalView : AppCompatActivity(){
    private lateinit var mealCardRecyclerView: RecyclerView
    private lateinit var mealCardAdapter: MealCardAdapter
    private lateinit var mealCardLayoutManager: RecyclerView.LayoutManager
    private lateinit var mealObjects: ArrayList<MealObject>
    private lateinit var ingredientObjects: ArrayList<IngredientObject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar?=supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_search_for_meals_local_view)

        mealObjects = ArrayList()
        ingredientObjects = ArrayList()

        //getting editText box and buttons
        val searchButton = findViewById<Button>(R.id.RetrieveMealLocalButton)

        //initialize the recycler view and layout manager
        mealCardRecyclerView = findViewById(R.id.mealCardLocalView)
        mealCardLayoutManager = LinearLayoutManager(this)
        mealCardRecyclerView.layoutManager = mealCardLayoutManager

        //initialize mealcard adapter
        mealCardAdapter = MealCardAdapter(mealObjects,ingredientObjects)
        mealCardRecyclerView.adapter = mealCardAdapter


        //setting up button to search for meal
        searchButton.setOnClickListener {
            mealObjects.clear()
            ingredientObjects.clear()
            mealCardAdapter.notifyDataSetChanged()

            searchMeals(this)
            mealCardAdapter.notifyDataSetChanged()

            mealCardAdapter = MealCardAdapter(mealObjects, ingredientObjects)
            mealCardRecyclerView.adapter = mealCardAdapter
            mealCardAdapter.notifyDataSetChanged()
        }
    }
    private fun searchMeals(context: Context) {
        val handler = Handler(Looper.getMainLooper())
        Toast.makeText(context, "Searching for meals", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val editText = findViewById<EditText>(R.id.searchMealLocalTextbox)
                val searchText = editText.text.toString().trim()
                val database = MealsDatabase.getInstance(context)
                val Dao = database.mealsDao()
                val meals = Dao.searchForMealsWithIngredients(searchText)
                println("adding meals to list")
                println(meals.size)
                println(meals[0].meal.mealName)
                val newMealObjects = mutableListOf<MealObject>()
                val newIngredientObjects = mutableListOf<IngredientObject>()
                for (element in meals) {
                    val mealId = element.meal.mealId.toString()
                    val mealName = element.meal.mealName.toString()
                    val mealImage = element.meal.mealImage.toString()
                    println(mealImage)
                    val mealDrink = element.meal.drinkAlt.toString()
                    val mealOrigin = element.meal.mealOrigin.toString()
                    val malDiet = element.meal.mealDiet.toString()
                    val mealTags = element.meal.mealTags.toString()
                    val mealInstructions = element.meal.mealInstructions.toString()
                    val mealVideo = element.meal.mealYoutube.toString()
                    val mealObject = MealObject(
                        mealId,
                        mealName,
                        mealImage,
                        mealDrink,
                        mealOrigin,
                        malDiet,
                        mealTags,
                        mealInstructions,
                        mealVideo
                    )
                    newMealObjects.add(mealObject)
                    for (mealsAdded in newMealObjects) {
                        println("Added:" +mealsAdded.mealName)
                    }
                    for (ingredient in element.ingredients) {
                        val ingredientName = ingredient.ingredientName.toString()
                        val ingredientMeasure = ingredient.ingredientMeasure.toString()
                        val ingredientId = ingredient.ingredientId.toString()
                        val ingredientObject = IngredientObject(
                            ingredientId,
                            mealId,
                            ingredientName,
                            ingredientMeasure
                        )
                        newIngredientObjects.add(ingredientObject)
                        for (ingredientsAdded in newIngredientObjects) {
                            println(ingredientsAdded.mealIngredients)
                        }
                    }
                }
                handler.post {
                    mealObjects.addAll(newMealObjects)
                    ingredientObjects.addAll(newIngredientObjects)
                    Toast.makeText(context, "Meals found", Toast.LENGTH_SHORT).show()
                }
                println("Meals added")
            }
            Handler(Looper.getMainLooper()).post {
                mealCardAdapter.notifyDataSetChanged()
            }
        }
    }


}