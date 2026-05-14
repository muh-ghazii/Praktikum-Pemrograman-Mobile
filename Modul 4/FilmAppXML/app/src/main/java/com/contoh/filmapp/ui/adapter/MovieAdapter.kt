package com.contoh.filmapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.contoh.filmapp.data.Movie
import com.contoh.filmapp.databinding.ItemMovieBinding

class MovieAdapter(
    private val onImdbClick: (Movie) -> Unit,
    private val onDetailClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.ViewHolder>(MovieDiffCallback()) {

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
    inner class ViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.imgMovie.load(movie.imageUrl)
            binding.tvTitle.text = movie.title
            binding.tvYear.text = movie.year.toString()
            binding.tvGenre.text = movie.genre
            binding.tvRating.text = "★ ${movie.rating}"
            binding.tvPlot.text = "Plot: ${movie.plot}"
            binding.btnImdb.setOnClickListener { onImdbClick(movie) }
            binding.btnDetail.setOnClickListener { onDetailClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}