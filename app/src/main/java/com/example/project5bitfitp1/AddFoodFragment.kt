package com.example.project5bitfitp1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "FoodListFragment"
private val foods = mutableListOf<DisplayFood>()
/**
 * A simple [Fragment] subclass.
 * Use the [FoodListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFoodFragment : Fragment() {

    // Add these properties
    private lateinit var name: TextView
    private lateinit var calories: TextView
    private lateinit var button: Button
    private lateinit var myApp: FoodApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_add_food, container, false)

        name = view.findViewById(R.id.foodNameText)
        calories = view.findViewById(R.id.foodCaloriesText)
        button = view.findViewById(R.id.button)
        myApp = FoodApplication(view.context)
        button.setOnClickListener {

            val myName = name.text.toString()
            val myCalories = calories.text.toString()

            // warns me that it could be null, but it works just fine
            if (myName != "" && myCalories != "") {
                val food = DisplayFood(myName, myCalories)
                foods.add(food)
                lifecycleScope.launch(Dispatchers.IO) {
                    myApp.db.foodDao().insertAll(foods.map {
                        FoodEntity(
                            name = it.name,
                            calories = it.calories
                        ).also {
                            //This way, it only adds one at a time
                            foods.clear()
                        }
                    })
                }
                Toast.makeText(context, "Food item added successfully", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context, "Please enter an input for both name and calories", Toast.LENGTH_LONG).show()
                Log.e("Error", "No food was added because one of the values is null")
            }

        }

        return view
    }

    companion object {

        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }
}