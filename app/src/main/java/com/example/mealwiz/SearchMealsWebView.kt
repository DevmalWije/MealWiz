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
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchMealsWebView : AppCompatActivity() {
    private lateinit var mealCardRecyclerView: RecyclerView
    private lateinit var mealCardAdapter: MealCardAdapter
    private lateinit var mealCardLayoutManager: RecyclerView.LayoutManager
    private lateinit var meals: ArrayList<MealObject>
    private lateinit var ingredients: ArrayList<IngredientObject>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar: ActionBar?=supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_search_meals_view)
        //initialize the array lists
        meals = ArrayList()
        ingredients = ArrayList()

        //initialize the recycler view and layout manager
        mealCardRecyclerView = findViewById(R.id.mealCardView)
        mealCardLayoutManager = LinearLayoutManager(this)
        mealCardRecyclerView.layoutManager = mealCardLayoutManager

        //initialize mealcard adapter
        mealCardAdapter = MealCardAdapter(meals,ingredients)
        mealCardRecyclerView.adapter = mealCardAdapter

        val searchMealButton=findViewById<Button>(R.id.RetrieveMealButton)
        val saveMealButton=findViewById<Button>(R.id.SaveMealToDBButton)
        saveMealButton.isEnabled = false

        searchMealButton.setOnClickListener {
            //clear the meals and ingredients array lists before making a new search
            meals.clear()
            ingredients.clear()
            //reset the adapter
            mealCardAdapter.notifyDataSetChanged()
            //make the new search
            searchMealByIngredients(this)
            //reset the adapter with the new data
            mealCardAdapter = MealCardAdapter(meals,ingredients)
            mealCardRecyclerView.adapter = mealCardAdapter
            saveMealButton.isEnabled = true
        }

        saveMealButton.setOnClickListener {
            //save the meal to the database
            addSearchToDatabase(this)
            Toast.makeText(this, "Meals saved to database", Toast.LENGTH_SHORT).show()
            saveMealButton.isEnabled = false
//            checkMealsAddedToDatabase(MealsDatabase.getInstance(this).mealsDao())
        }

    }

    //function to make a search by ingredients
fun searchMealByIngredients(context: Context) {
        val handler = Handler(Looper.getMainLooper())
    Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show()
    lifecycleScope.launch {
        withContext(Dispatchers.IO) {
            //Getting users search query from the searchMealTextbox
            val searchByIngredientBox = findViewById<EditText>(R.id.searchMealTextbox)
            val ingredient = searchByIngredientBox.text.toString().trim()
            val mealIdList = ArrayList<String>()
            val searchQueryURL =
                URL("https://www.themealdb.com/api/json/v1/1/filter.php?i=$ingredient")
            //making request to get mealId's of all meals having an ingredient that matches the user's search query
            val connection = searchQueryURL.openConnection() as HttpURLConnection
            connection.readTimeout = 10000
            connection.requestMethod = "GET"
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
                            mealIdList.add(mealId)
                        }
                    } else {
                        handler.post {
                            Toast.makeText(
                                context,
                                "No meals found matching your query",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                    if (mealIdList.size > 0) {
                        handler.post {
                            Toast.makeText(context, "Found ${mealIdList.size} meals", Toast.LENGTH_SHORT)
                                .show()
                        }
                        mealIdList.forEach { mealId ->
                            val mealQueryURL =
                                URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=$mealId")
                            val mealRequestConnection =
                                mealQueryURL.openConnection() as HttpURLConnection
                            mealRequestConnection.readTimeout = 10000
                            mealRequestConnection.requestMethod = "GET"
                            val mealResponseCode = mealRequestConnection.responseCode
                            if (mealResponseCode == HttpURLConnection.HTTP_OK) {
                                val mealInputStream = mealRequestConnection.inputStream
                                val mealBufferedReader =
                                    BufferedReader(InputStreamReader(mealInputStream))
                                val mealResponseStringBuilder = StringBuilder()
                                var mealLine: String?
                                while (mealBufferedReader.readLine()
                                        .also { mealLine = it } != null
                                ) {
                                    mealResponseStringBuilder.append(mealLine)
                                }
                                mealBufferedReader.close()
                                mealInputStream.close()
                                val mealResponse = mealResponseStringBuilder.toString()
                                try {
                                    val jsonMealObject = JSONObject(mealResponse)
                                    val jsonMealArray = jsonMealObject.getJSONArray("meals")
                                    //adding all meals to the meals array
                                    for (i in 0 until jsonMealArray.length()) {
                                        val meal = jsonMealArray.getJSONObject(i)
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
                                            mealDiet,
                                            mealOrigin,
                                            mealTags,
                                            mealInstructions,
                                            mealVideo
                                        )
                                        meals.add(mealObject)
                                        //adding all ingredients to the ingredients array
                                        for (j in 1..20) {
                                            val ingredient = meal.getString("strIngredient$j")
                                            val measure = meal.getString("strMeasure$j")
                                            val ingredientObject = IngredientObject(
                                                j.toString(),
                                                mealObject.mealId,
                                                ingredient,
                                                measure
                                            )
                                            ingredients.add(ingredientObject)
                                        }
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                        Handler(Looper.getMainLooper()).post {
                            mealCardAdapter.notifyDataSetChanged()
                        }
                    } else {
                       handler.post {
                           Toast.makeText(
                               context,
                               "No meals found matching your query",
                               Toast.LENGTH_SHORT
                           )
                               .show()
                       }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                } catch (e: java.lang.NullPointerException) {
                   handler.post {
                       Toast.makeText(
                           context,
                           "No meals found matching your query",
                           Toast.LENGTH_SHORT
                       )
                           .show()
                   }
                }
            } else {
              handler.post {
                  Toast.makeText(
                      context,
                      "API request failed",
                      Toast.LENGTH_SHORT
                  )
                      .show()
              }
            }
        }
    }

}
    //fon to save the meals from the search to the database
    private fun addSearchToDatabase(context: Context) {
        GlobalScope.launch {
            val database = MealsDatabase.getInstance(context)
            val Dao = database.mealsDao()

            for (i in 0 until meals.size) {
                val meal=MealEntity(
                    mealId = meals[i].mealId.toInt(),
                    mealName = meals[i].mealName,
                    mealImage = meals[i].mealImage,
                    drinkAlt = meals[i].drinkAlt,
                    mealDiet = meals[i].mealDiet,
                    mealOrigin = meals[i].mealOrigin,
                    mealTags = meals[i].mealTags,
                    mealInstructions = meals[i].mealInstructions,
                    mealYoutube = meals[i].mealYoutube,
                    ImageSource = null,
                    CreativeCommonsConfirmed = null,
                    dateModified = null
                )
                Dao.insertMeal(meal)
                //adding ingredients to the database
                for (x in 0 until 20) {
                    val ingredient = IngredientEntity(
                        mealId = meals[i].mealId.toInt(),
                        ingredientName = ingredients[i*20+x].mealIngredients,
                        ingredientMeasure = ingredients[i*20+x].mealMeasurements
                    )
                    Dao.insertIngredient(ingredient)
                }
            }
        }
    }
    fun checkMealsAddedToDatabase(mealsDao: MealsDao) {
        GlobalScope.launch {
            val meals = mealsDao.getAllMeals()
            for (meal in meals) {
                println(meal.mealName)
            }
        }
    }
}