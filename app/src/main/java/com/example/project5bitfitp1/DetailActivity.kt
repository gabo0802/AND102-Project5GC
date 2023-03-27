package com.example.project5bitfitp1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var name: TextView
    private lateinit var calories: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        name = findViewById(R.id.foodNameText)
        calories = findViewById(R.id.foodCaloriesText)
        button = findViewById(R.id.button)

        button.setOnClickListener {
            val data = Intent(this, MainActivity::class.java)
            data.putExtra(
                "name",
                name.text.toString()
            )
            data.putExtra(
                "calories",
                calories.text.toString()
            )
            setResult(RESULT_OK, data)
            finish()
        }

    }
}

//TODO: From lab
/*

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bylineTextView: TextView
    private lateinit var abstractTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        bylineTextView = findViewById(R.id.mediaByline)
        abstractTextView = findViewById(R.id.mediaAbstract)

        // TODO: Get the extra from the Intent
        val article = intent.getSerializableExtra(ARTICLE_EXTRA) as DisplayArticle

        // TODO: Set the title, byline, and abstract information from the article
        titleTextView.text = article.headline
        bylineTextView.text = article.byline
        abstractTextView.text = article.abstract

        // TODO: Load the media image
        Glide.with(this)
            .load(article.mediaImageUrl)
            .into(mediaImageView)
    }
}

 */