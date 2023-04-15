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

//SearchForMealsLocalView.kt
class SearchForMealsLocalView : AppCompatActivity(){
    //initialize recycler view and layout manager and adapter for the recycler view
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

        //initialize meal and ingredient objects
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
    //searchMeals function
    private fun searchMeals(context: Context) {
        //handler to make toast
        val handler = Handler(Looper.getMainLooper())
        Toast.makeText(context, "Searching for meals", Toast.LENGTH_SHORT).show()
        //launch coroutine to search for meals
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                //getting text from search box
                val editText = findViewById<EditText>(R.id.searchMealLocalTextbox)
                val searchText = editText.text.toString().trim()
                val database = MealsDatabase.getInstance(context)
                val Dao = database.mealsDao()
                //searching for meals with ingredients
                val meals = Dao.searchForMealsWithIngredients(searchText)
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