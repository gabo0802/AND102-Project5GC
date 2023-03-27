package com.example.project5bitfitp1

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(private val context: Context, private val foods: List<DisplayFood>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // TODO: Get the individual article and bind to holder
            val food = foods[position]
            holder.bind(food)
        }

        override fun getItemCount() = foods.size

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

            private val name = itemView.findViewById<TextView>(R.id.name)
            private val calories = itemView.findViewById<TextView>(R.id.calories)

            init {
                itemView.setOnClickListener(this)
            }

            // TODO: Write a helper method to help set up the onBindViewHolder method
            fun bind(food: DisplayFood) {
                name.text = food.name
                calories.text = food.calories

            }

            override fun onClick(v: View?) {
                //TODO: probably not necessary
            }
        }
}

// TODO: From P4
/*
class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Get the individual article and bind to holder
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.mediaImage)
        private val titleTextView = itemView.findViewById<TextView>(R.id.mediaTitle)
        private val abstractTextView = itemView.findViewById<TextView>(R.id.mediaAbstract)

        init {
            itemView.setOnClickListener(this)
        }

        // TODO: Write a helper method to help set up the onBindViewHolder method
        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            abstractTextView.text = movie.description
            val radius = 30
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${movie.imageURL}")
                .centerCrop()
                .transform(RoundedCorners(radius))
                .into(mediaImageView)
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val movie = movies[absoluteAdapterPosition]
            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)
        }
    }
}
 */