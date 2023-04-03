package com.example.project5bitfitp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "FoodListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StatsFragment : Fragment() {

    // Add these properties
    private val foods = mutableListOf<DisplayFood>()
    private lateinit var foodsAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        foodsAdapter = FoodAdapter(view.context, foods)

        val avgCals = view.findViewById<TextView>(R.id.avgCalories)
        val totalCals = view.findViewById<TextView>(R.id.totalCalories)
        val mostCals = view.findViewById<TextView>(R.id.mostCaloric)

        val myApp = FoodApplication(view.context)
        myApp.initFirstFood()

        //TODO: Error happens here, I have to load the frame twice to fix it
        lifecycleScope.launch {
            myApp.db.foodDao().getAll().collect { databaseList ->
                databaseList.map { it ->
                    DisplayFood(
                        name = it.name,
                        calories = it.calories
                    )
                }.also { mappedList ->
                    foods.clear()
                    foods.addAll(mappedList)
                    foodsAdapter.notifyDataSetChanged()
                }
            }
        }

        var totalCalories = 0;
        var mostCalories = 0
        var i = 0
        for(food in foods) {
            val cur = food.calories?.toInt() ?: 0
            totalCalories += cur
            i++
            if(cur > mostCalories) {
                mostCalories = cur
            }
        }
        if(i == 0) {
            i = 1
            Toast.makeText(view.context, "Error Happened, foods were not loaded", Toast.LENGTH_SHORT).show()
        }
        avgCals.text = (totalCalories / i).toString()
        totalCals.text = totalCalories.toString()
        mostCals.text = mostCalories.toString()

        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {

        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }
}