package com.contoh.filmapp.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import com.contoh.filmapp.data.MovieData
import com.contoh.filmapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt("movieId") ?: 0
        val movie = MovieData.movies.find { it.id == movieId }

        if (movie == null) {
            findNavController().popBackStack()
            return
        }
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbarDetail.title = movie.title
        binding.imgDetail.load(movie.imageUrl)
        binding.tvDetailTitle.text = movie.title
        binding.tvDetailYear.text = movie.year.toString()
        binding.chipGenre.text = movie.genre
        binding.tvDetailRating.text = "${movie.rating} / 10"
        binding.tvDetailPlot.text = movie.plot
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}