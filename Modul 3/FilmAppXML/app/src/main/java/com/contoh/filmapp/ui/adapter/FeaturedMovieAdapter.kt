package com.contoh.filmapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.contoh.filmapp.data.Movie
import com.contoh.filmapp.databinding.ItemFeaturedMovieBinding

class FeaturedMovieAdapter(
    private val movies: List<Movie>
) : RecyclerView.Adapter<FeaturedMovieAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemFeaturedMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.imgFeatured.load(movie.imageUrl)
            binding.tvFeaturedTitle.text = movie.title
            binding.tvFeaturedRating.text = "★ ${movie.rating}"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFeaturedMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }
    override fun getItemCount() = movies.size
}