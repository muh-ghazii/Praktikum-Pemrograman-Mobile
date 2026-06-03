package com.contoh.filmapp.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.contoh.filmapp.databinding.FragmentDetailBinding
import com.contoh.filmapp.viewmodel.MovieViewModel
import com.contoh.filmapp.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory("FilmApp")
    }

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
        binding.toolbarDetail.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect { movies ->
                    val movie = movies.find { it.id == movieId }

                    if (movie == null) {
                        findNavController().popBackStack()
                        return@collect
                    }
                    binding.toolbarDetail.title = movie.title
                    binding.imgDetail.load(movie.imageUrl)
                    binding.tvDetailTitle.text = movie.title
                    binding.tvDetailYear.text = movie.year.toString()
                    binding.chipGenre.text = movie.genre
                    binding.tvDetailRating.text = "${movie.rating} / 10"
                    binding.tvDetailPlot.text = movie.plot
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}