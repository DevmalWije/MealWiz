package com.example.mealwiz

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//ViewHolder class for the RecyclerView in the SearchMealsView.kt file.
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mealName: TextView = itemView.findViewById(R.id.mealName)
    var mealImage: ImageView = itemView.findViewById(R.id.mealImage)
    var mealDrink: TextView = itemView.findViewById(R.id.mealDrinkAlternate)
    var mealOrigin: TextView = itemView.findViewById(R.id.mealOrigin)
    var mealDiet: TextView = itemView.findViewById(R.id.mealDiet)
    var mealTags: TextView = itemView.findViewById(R.id.mealTags)
    var mealIngredients: TextView = itemView.findViewById(R.id.mealIngredients)
    var mealMeasurements: TextView = itemView.findViewById(R.id.mealMeasurements)
    var mealInstructions: TextView = itemView.findViewById(R.id.mealInstructions)
    var mealVideo: TextView = itemView.findViewById(R.id.mealVideo)


}

