package com.example.mealwiz

data class MealObject(
    var mealId: String,
    var mealName: String,
    var mealImage: String,
    var drinkAlt: String,
    var mealDiet: String,
    var mealOrigin: String,
    var mealTags: String,
    var mealInstructions: String,
    var mealYoutube: String
)

////dummy data for the RecyclerView in the SearchMealsView.kt file.
//val meals = listOf(
//    MealObject(
//        "1",
//        "Chicken Parmesan",
//        "https:\\/\\/www.themealdb.com\\/images\\/media\\/meals\\/ustsqw1468250014.jpg",
//        "Soda",
//        "Vegetarian",
//        "Italian",
//        "#Chicken, #Parmesan, #Pasta",
//        "Chicken, Parmesan, Pasta",
//        "1/2 cup, 1/2 cup, 1/2 cup",
//        "Cook the chicken, cook the pasta, mix it all together",
//        "https://www.youtube.com/watch?v=1IszT_guI08"
//    ),
//    MealObject(
//        "2",
//        "Spicy Chicken Parmesan",
//        "chicken_parmesan",
//        "Soda",
//        "Vegetarian",
//        "Italian",
//        "Chicken, Parmesan, Pasta",
//        "Chicken, Parmesan, Pasta",
//        "1/2 cup, 1/2 cup, 1/2 cup",
//        "Cook the chicken, cook the pasta, mix it all together",
//        "https://www.youtube.com/watch?v=1IszT_guI08"
//    ),
//    MealObject(
//        "3",
//        "booty Parmesan",
//        "chicken_parmesan",
//        "Soda",
//        "Vegetarian",
//        "Italian",
//        "Chicken, Parmesan, Pasta",
//        "Chicken, Parmesan, Pasta",
//        "1/2 cup, 1/2 cup, 1/2 cup",
//        "Cook the chicken, cook the pasta, mix it all together",
//        "https://www.youtube.com/watch?v=1IszT_guI08"
//    ),
//    MealObject(
//        "4",
//        "Chicken booty",
//        "chicken_parmesan",
//        "Soda",
//        "Vegetarian",
//        "Italian",
//        "Chicken, Parmesan, Pasta",
//        "Chicken, Parmesan, Pasta",
//        "1/2 cup, 1/2 cup, 1/2 cup",
//        "Cook the chicken, cook the pasta, mix it all together",
//        "https://www.youtube.com/watch?v=1IszT_guI08"
//    ))