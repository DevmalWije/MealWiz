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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMealByName : AppCompatActivity() {
    private lateinit var mealCardRecyclerView: RecyclerView
    private lateinit var mealCardAdapter: MealCardAdapter
    private lateinit var mealCardLayoutManager: RecyclerView.LayoutManager
    private lateinit var meals: ArrayList<MealObject>
    private lateinit var ingredients: ArrayList<IngredientObject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar?=supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_search_meal_by_name)
        //initializing object arrays
        meals = ArrayList()
        ingredients = ArrayList()

        //initializing recycler view and layout manager
        mealCardRecyclerView = findViewById(R.id.mealCardAPIView)
        mealCardLayoutManager = LinearLayoutManager(this)
        mealCardRecyclerView.layoutManager = mealCardLayoutManager

        //initializing adapter
        mealCardAdapter = MealCardAdapter(meals, ingredients)
        mealCardRecyclerView.adapter = mealCardAdapter

        val searchButton = findViewById<Button>(R.id.searchMealByNameButton)

        searchButton.setOnClickListener {
            meals.clear()
            ingredients.clear()
            mealCardAdapter.notifyDataSetChanged()
            SearchMealByNameOnline(this)
            mealCardAdapter= MealCardAdapter(meals, ingredients)
            mealCardRecyclerView.adapter = mealCardAdapter
        }
    }

    fun SearchMealByNameOnline(context: Context){
        val handler=Handler(Looper.getMainLooper())
        Toast.makeText(context, "Searching...", Toast.LENGTH_SHORT).show()
        lifecycleScope.launch{
            withContext(Dispatchers.IO) {
                //get meal name from search bar
                val mealName = findViewById<EditText>(R.id.searchMealByNameTextbox)
                val mealNameString = mealName.text.toString().trim()
                if (mealNameString.isEmpty()) {
                    handler.post {
                        Toast.makeText(context, "Please enter a meal name", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    val searchQueryURL =
                        URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$mealNameString")

                    //make API call connection
                    val connection = searchQueryURL.openConnection() as HttpURLConnection
                    connection.readTimeout = 10000
                    connection.requestMethod = "GET"

                    //get response code
                    val responseCode = connection.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        val inputStream = connection.inputStream
                        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                        val responseStringBuilder = StringBuilder()
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            responseStringBuilder.append(line)
                        }
                        bufferedReader.close()
                        inputStream.close()
                        val response = responseStringBuilder.toString()
                        try {
                            val jsonObject = JSONObject(response)
                            if (jsonObject.has("meals") && !jsonObject.isNull("meals")) {
                                val jsonArray = jsonObject.getJSONArray("meals")
                                for (i in 0 until jsonArray.length()) {
                                    val meal = jsonArray.getJSONObject(i)
                                    val mealId = meal.getString("idMeal")
                                    val mealName = meal.getString("strMeal")
                                    val mealImage = meal.getString("strMealThumb")
                                    val mealDrink = meal.getString("strDrinkAlternate")
                                    val mealOrigin = meal.getString("strArea")
                                    val mealDiet = meal.getString("strCategory")
                                    val mealTags = meal.getString("strTags")
                                    val mealInstructions = meal.getString("strInstructions")
                                    val mealVideo = meal.getString("strYoutube")
                                    val mealObject = MealObject(
                                        mealId,
                                        mealName,
                                        mealImage,
                                        mealDrink,
                                        mealOrigin,
                                        mealDiet,
                                        mealTags,
                                        mealInstructions,
                                        mealVideo
                                    )
                                    meals.add(mealObject)
                                    //get ingredients
                                    for (x in 1..20) {
                                        val ingredientName = meal.getString("strIngredient$x")
                                        val ingredientMeasure = meal.getString("strMeasure$x")
                                        val ingredientObject = IngredientObject(
                                            x.toString(),
                                            mealObject.mealId,
                                            ingredientName,
                                            ingredientMeasure
                                        )
                                        ingredients.add(ingredientObject)
                                    }

                                }
                            } else {
                                handler.post {
                                    Toast.makeText(context, "No meals found", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    } else {
                        handler.post {
                            Toast.makeText(
                                context,
                                "API request failed: Error: $responseCode",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                Handler(Looper.getMainLooper()).post {
                    mealCardAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}