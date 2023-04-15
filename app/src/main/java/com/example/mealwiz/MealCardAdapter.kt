package com.example.mealwiz

import android.graphics.BitmapFactory
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.net.HttpURLConnection
import java.net.URL

// MealCardAdapter class
class MealCardAdapter(private val meals: List<MealObject>, private val ingredients: List<IngredientObject>) :
    RecyclerView.Adapter<MealCardAdapter.MealCardViewHolder>() {

    // MealCardViewHolder class
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.meal_card, parent, false)
        return MealCardViewHolder(view)
    }

    // onBindViewHolder function
    override fun onBindViewHolder(holder: MealCardViewHolder, position: Int) {
        val meal = meals[position]
        // filter ingredients and measurements
        val ingredientsList = ingredients.filter { it.mealId == meal.mealId && it.mealIngredients != " " && it.mealIngredients != "null" && it.mealIngredients.isNotEmpty() }
        val measurementList = ingredients.filter { it.mealId == meal.mealId && it.mealMeasurements != " " && it.mealIngredients != "null" && it.mealIngredients.isNotEmpty() }
        val ingredientString = ingredientsList.joinToString(separator = "\n") { it.mealIngredients }
        val measurementString = measurementList.joinToString(separator = "\n") { it.mealMeasurements }
        holder.mealImage.setImageResource(R.drawable.loading2)
        //load image from URL
        Thread {
            val imageURL = URL(meal.mealImage)
            val connection = imageURL.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.readTimeout = 5000
            connection.connect()
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val input = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(input)
                holder.mealImage.post {
                    holder.mealImage.setImageBitmap(bitmap)
                }
            }
            connection.disconnect()
        }.start()
        holder.mealName.text = meal.mealName
        holder.mealOrigin.text = meal.mealOrigin
        holder.mealDrink.text = meal.drinkAlt
        holder.mealDiet.text = meal.mealDiet
        holder.mealTags.text = meal.mealTags
        holder.mealIngredients.text = ingredientString
        holder.mealMeasurements.text = measurementString
        holder.mealInstructions.text = meal.mealInstructions
        holder.mealVideo.text = meal.mealYoutube
        Linkify.addLinks(holder.mealVideo, Linkify.WEB_URLS)
    }
    override fun getItemCount(): Int {
        return meals.size
    }
    // MealCardViewHolder inner class
    inner class MealCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mealImage: ImageView = itemView.findViewById(R.id.mealImage)
        var mealName: TextView = itemView.findViewById(R.id.mealName)
        var mealOrigin: TextView = itemView.findViewById(R.id.mealOrigin)
        var mealDrink: TextView = itemView.findViewById(R.id.mealDrinkAlternate)
        var mealDiet: TextView = itemView.findViewById(R.id.mealDiet)
        var mealTags: TextView = itemView.findViewById(R.id.mealTags)
        var mealIngredients: TextView = itemView.findViewById(R.id.mealIngredients)
        var mealMeasurements: TextView = itemView.findViewById(R.id.mealMeasurements)
        var mealInstructions: TextView = itemView.findViewById(R.id.mealInstructions)
        var mealVideo: TextView = itemView.findViewById(R.id.mealVideo)
    }
}
