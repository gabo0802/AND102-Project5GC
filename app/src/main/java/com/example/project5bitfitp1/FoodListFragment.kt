package com.example.project5bitfitp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class FoodListFragment : Fragment() {

    // Add these properties
    private val foods = mutableListOf<DisplayFood>()
    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var foodsAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_food_list, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        foodsRecyclerView = view.findViewById(R.id.food_recycler_view)
        foodsRecyclerView.layoutManager = layoutManager
        foodsRecyclerView.setHasFixedSize(true)
        foodsAdapter = FoodAdapter(view.context, foods)
        foodsRecyclerView.adapter = foodsAdapter
        foodsRecyclerView.layoutManager = LinearLayoutManager(view.context).also {
            val dividerItemDecoration = DividerItemDecoration(view.context, it.orientation)
            foodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val myApp = FoodApplication(view.context)
        myApp.initFirstFood()


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

        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {

        fun newInstance(): FoodListFragment {
            return FoodListFragment()
        }
    }
}