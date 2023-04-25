package com.example.mealwiz
//link to video demonstration  https://drive.google.com/file/d/1pQubNTehPypTCRFyg1lTHtnlVOod6fre/view?usp=share_link
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar:ActionBar?=supportActionBar
        actionBar?.hide()
        setContentView(R.layout.activity_main)

        //Adding meals to dictionary
        val mealsDict= mapOf(
            "meal1" to mapOf(
            "Meal" to "Sweet and Sour Pork",
            "DrinkAlternate" to null,
            "Category" to "Pork",
            "Area" to "Chinese",
            "Instructions" to "Preparation \\r \\n1. Crack the egg into a bowl. Separate the egg white and yolk. \\r \\n \\r \\nSweet and Sour Pork \\r \\n2. Slice the pork tenderloin into ips. \\r \\n \\r \\n3. Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white. \\r \\n \\r \\n4. Marinade the pork ips for about 20 minutes. \\r \\n \\r \\n5. Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce. \\r \\n \\r \\nSweet and Sour Pork \\r \\nCooking Inuctions \\r \\n1. Pour the cooking oil into a wok and heat to 190 \\u00b0C (375 \\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate. \\r \\n \\r \\n2. Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined. \\r \\n \\r \\n3. Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it. \\r \\n \\r \\n4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together. \\r \\n \\r \\n5. Serve on a plate and add some coriander for decoration.",
            "MealThumb" to "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/1529442316.jpg",
            "Tags" to "Sweet",
            "Youtube" to "https\\/ \\/www.youtube.com \\/watch?v=mdaBIhgEAMo",
            "Ingredient1" to "Pork",
            "Ingredient2" to "Egg",
            "Ingredient3" to "Water",
            "Ingredient4" to "Salt",
            "Ingredient5" to "Sugar",
            "Ingredient6" to "Soy Sauce",
            "Ingredient7" to "Starch",
            "Ingredient8" to "Tomato Puree",
            "Ingredient9" to "Vinegar",
            "Ingredient10" to "Coriander",
            "Measure1" to "200g",
            "Measure2" to "1",
            "Measure3" to "Dash",
            "Measure4" to "1 \\/2 tsp",
            "Measure5" to "1 tsp ",
            "Measure6" to "10g",
            "Measure7" to "10g",
            "Measure8" to "30g",
            "Measure9" to "10g",
            "Measure10" to "Dash"
            ),
            "meal2" to mapOf(
            "Meal" to "Chicken Marengo",
            "DrinkAlternate" to null,
            "Category" to "Chicken",
            "Area" to "French",
            "Instructions" to "Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little. \\r \\nPour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper  \\u2013 you shouldn \\u2019t need salt. Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.",
            "MealThumb" to "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/qpxvuq1511798906.jpg" ,
            "Tags" to null,
            "Youtube" to "https\\/ \\/www.youtube.com \\/watch?v=U33HYUr-0Fw",
            "Ingredient1" to "Olive Oil",
            "Ingredient2" to "Mushrooms",
            "Ingredient3" to "Chicken Legs",
            "Ingredient4" to "Passata",
            "Ingredient5" to "Chicken Stock Cube",
            "Ingredient6" to "Black Olives",
            "Ingredient7" to "Parsley",
            "Measure1" to "1 tbs",
            "Measure2" to "300g",
            "Measure3" to "4",
            "Measure4" to "500g",
            "Measure5" to "1",
            "Measure6" to "100g ",
            "Measure7" to "Chopped",
            "Source" to "https\\/ \\/www.bbcgoodfood.com \\/recipes \\/3146682 \\/chicken-marengo",
            "ImageSource" to null,
            "CreativeCommonsConfirmed" to null,
            "dateModified" to null
            ),
            "meal3" to mapOf(
            "Meal" to "Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber",
            "DrinkAlternate" to null,
            "Category" to "Beef",
            "Area" to "Vietnamese",
            "Instructions" to "Add'l ingredients to  mayonnaise, siracha \\r \\n \\r \\n1 \\r \\n \\r \\nPlace rice in a fine-mesh sieve and rinse until water runs clear. Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or until ready to serve. \\r \\n \\r \\n2 \\r \\n \\r \\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot. \\r \\n \\r \\n3 \\r \\n \\r \\nIn a medium bowl, combine cucumber, juice from half the lime,  \\u00bc tsp sugar ( \\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you \\u2019d like. Season with salt and pepper. \\r \\n \\r \\n4 \\r \\n \\r \\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper. \\r \\n \\r \\n5 \\r \\n \\r \\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.",
            "MealThumb" to "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/z0ageb1583189517.jpg",
            "Tags" to null,
            "Youtube" to "",
            "Ingredient1" to "Rice",
            "Ingredient2" to "Onion",
            "Ingredient3" to "Lime",
            "Ingredient4" to "Garlic Clove",
            "Ingredient5" to "Cucumber",
            "Ingredient6" to "Carrots",
            "Ingredient7" to "Ground Beef",
            "Ingredient8" to "Soy Sauce",
            "Ingredient9" to "",
            "Measure1" to "White",
            "Measure2" to "1",
            "Measure3" to "1",
            "Measure4" to "3",
            "Measure5" to "1",
            "Measure6" to "3 oz ",
            "Measure7" to "1 lb",
            "Measure8" to "2 oz ",
            "Source" to "",
            "ImageSource" to null,
            "CreativeCommonsConfirmed" to null,
            "dateModified" to null
            ),
            "meal4" to mapOf(
            "Meal" to "Leblebi Soup",
            "DrinkAlternate" to null,
            "Category" to "Vegetarian",
            "Area" to "Tunisian",
            "Instructions" to "Heat the oil in a large pot. Add the onion and cook until translucent. \\r \\nDrain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes. \\r \\nIn the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. Add the garlic and salt and pound to a fine paste. \\r \\nAdd the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes. \\r \\nSeason to taste with salt, pepper and lemon juice and serve hot.",
            "MealThumb" to "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/x2fw9e1560460636.jpg",
            "Tags" to "Soup",
            "Youtube" to "https\\/ \\/www.youtube.com \\/watch?v=BgRifcCwinY",
            "Ingredient1" to "Olive Oil",
            "Ingredient2" to "Onion",
            "Ingredient3" to "Chickpeas",
            "Ingredient4" to "Vegetable Stock",
            "Ingredient5" to "Cumin",
            "Ingredient6" to "Garlic",
            "Ingredient7" to "Salt",
            "Ingredient8" to "Harissa Spice",
            "Ingredient9" to "Pepper",
            "Ingredient10" to "Lime",
            "Measure1" to "2 tbs",
            "Measure2" to "1 medium finely diced",
            "Measure3" to "250g",
            "Measure4" to "1.5L",
            "Measure5" to "1 tsp ",
            "Measure6" to "5 cloves",
            "Measure7" to "1 \\/2 tsp",
            "Measure8" to "1 tsp ",
            "Measure9" to "Pinch",
            "Measure10" to "1 \\/2 ",
            "Source" to "http to  \\/ \\/allrecipes.co.uk \\/recipe \\/43419 \\/leblebi--tunisian-chickpea-soup-.aspx",
            "ImageSource" to null,
            "CreativeCommonsConfirmed" to null,
            "dateModified" to null
            ),
        )
        val mealsJson=JSONArray(mealsDict.values)
        //adding meals saved in meals.txt file to the database
        val addMealsTButton = findViewById<Button>(R.id.mealToDbButton)
        addMealsTButton.setOnClickListener{
            addLocalMealsToDb(this, mealsJson)
            Toast.makeText(this, "Meals added to database", Toast.LENGTH_SHORT).show()
            addMealsTButton.isEnabled=false
            checkMealsAddedToDatabase(MealsDatabase.getInstance(this).mealsDao())
        }

        //Button to switch to the SearchMealsView.kt file
        val searchMealsButton = findViewById<Button>(R.id.searchByIngredientButton)
        searchMealsButton.setOnClickListener {
            val intent = Intent(this, SearchMealsWebView::class.java)
            startActivity(intent)
        }

        //Button to switch to the SearchForMealsLocalView.kt file
        val searchMealsLocalButton = findViewById<Button>(R.id.searchForMealButton)
        searchMealsLocalButton.setOnClickListener {
            val intent = Intent(this, SearchForMealsLocalView::class.java)
            startActivity(intent)
        }

        //Button to switch to the SearchForMealsOnlineView.kt file
        val searchMealByNameButton = findViewById<Button>(R.id.searchForMealButtonOnline)
        searchMealByNameButton.setOnClickListener {
            val intent = Intent(this, SearchMealByName::class.java)
            startActivity(intent)
        }
    }


    //function to add meals saved in dictionary  to the database
    private fun addLocalMealsToDb(context: Context, mealsJson: JSONArray){
       GlobalScope.launch{
           val database = MealsDatabase.getInstance(context)
           val Dao = database.mealsDao()

           for (i in 0 until mealsJson.length()){
                val mealJson=mealsJson.getJSONObject(i)
                val meal=MealEntity(
                    mealId=mealJson.optInt("idMeal",i),
                    mealName=mealJson.optString("Meal",null),
                    mealImage=mealJson.optString("MealThumb",null),
                    drinkAlt=mealJson.optString("DrinkAlternate",null),
                    mealDiet=mealJson.optString("Category",null),
                    mealOrigin=mealJson.optString("Area",null),
                    mealTags=mealJson.optString("Tags",null),
                    mealInstructions=mealJson.optString("Instructions",null),
                    mealYoutube=mealJson.optString("Youtube",null),
                    ImageSource=mealJson.optString("ImageSource",null),
                    CreativeCommonsConfirmed=mealJson.optString("CreativeCommonsConfirmed",null),
                    dateModified=mealJson.optString("dateModified",null)
                )
                Dao.insertMeal(meal)
                //adding ingredients to the database
                val ingredientsList= mutableListOf<IngredientEntity>()
                for (j in 1..20){
                    val ingredientName=mealJson.optString("Ingredient$j",null)
                    val ingredientMeasure=mealJson.optString("Measure$j",null)
                        val ingredient=IngredientEntity(
                            mealId=meal.mealId,
                            ingredientName=ingredientName,
                            ingredientMeasure=ingredientMeasure
                        )
                        ingredientsList.add(ingredient)
                }
                Dao.insertIngredients(ingredientsList)
            }
        }
    }
    //function to check if meals were added to the database
    fun checkMealsAddedToDatabase(mealsDao: MealsDao) {
        GlobalScope.launch {
            val meals = mealsDao.getAllMeals()
            for (meal in meals) {
                println(meal.mealName)
            }
        }
    }

}