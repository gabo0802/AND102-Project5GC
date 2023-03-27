package com.example.project5bitfitp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {

    private lateinit var foodsRecyclerView: RecyclerView
    private lateinit var addButton: Button
    private var foods = mutableListOf<DisplayFood>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foodsRecyclerView = findViewById(R.id.foods)

        val foodAdapter = FoodAdapter(this, foods)
        foodsRecyclerView.adapter = foodAdapter
        foodsRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            foodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }
        val myApp = FoodApplication(this)
        myApp.initFirstFood()
        addButton = findViewById(R.id.add)

        lifecycleScope.launch(IO) {
            myApp.db.foodDao().insertAll(foods.map {
                FoodEntity(
                    name = it.name,
                    calories = it.calories
                )
            })
        }

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data: Intent? = result.data
                if (data != null) {
                    val name = data.getStringExtra("name")
                    val calories = data.getStringExtra("calories")

                    Log.i("MainActivity", "Name: $name")
                    Log.i("MainActivity", "Calories: $calories")

                    //TODO: Set the name and calories of new food
                    //flashcardQuestion.text = question
                    //flashcardAnswer.text = answer
                    if(name != null && calories != null) {
                        val newFood = DisplayFood(name, calories)
                        foods.clear()
                        foods.add(newFood)

                        lifecycleScope.launch(IO) {
                            myApp.db.foodDao().insertAll(foods.map {
                                FoodEntity(
                                    name = it.name,
                                    calories = it.calories
                                )
                            })
                        }

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
                                    foodAdapter.notifyDataSetChanged()
                                }
                            }
                        }

                    } else {
                        Log.e("TAG","Missing name or calorie to input into database. Name is $name and Calories is $calories")
                    }
                } else {
                    Log.i("MainActivity", "Returned null data from AddCardActivity")
                }
            }

        addButton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            resultLauncher.launch(intent)
        }

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
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}

//TODO: From lab
/*
fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val ARTICLE_SEARCH_URL =
    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"

class MainActivity : AppCompatActivity() {
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private var articles = mutableListOf<DisplayArticle>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        articlesRecyclerView = findViewById(R.id.articles)
        // TODO: Set up ArticleAdapter with articles
        val articleAdapter = ArticleAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter

        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        val client = AsyncHttpClient()
        client.get(ARTICLE_SEARCH_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    // TODO: Create the parsedJSON
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString())

                    parsedJson.response?.docs?.let { list ->
                        lifecycleScope.launch(IO) {
                            (application as ArticleApplication).db.articleDao().deleteAll()
                            (application as ArticleApplication).db.articleDao().insertAll(list.map {
                                ArticleEntity(
                                    headline = it.headline?.main,
                                    articleAbstract = it.abstract,
                                    byline = it.byline?.original,
                                    mediaImageUrl = it.mediaImageUrl
                                )
                            })
                        }
                    }

                    // Reload the screen
                    articleAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })



        lifecycleScope.launch {
            (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayArticle(
                        entity.headline,
                        entity.articleAbstract,
                        entity.byline,
                        entity.mediaImageUrl
                    )
                }.also { mappedList ->
                    articles.clear()
                    articles.addAll(mappedList)
                    articleAdapter.notifyDataSetChanged()
                }
            }
        }


    }
}

 */