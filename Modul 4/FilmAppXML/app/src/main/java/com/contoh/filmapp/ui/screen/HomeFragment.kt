package com.contoh.filmapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.contoh.filmapp.R
import com.contoh.filmapp.databinding.FragmentHomeBinding
import com.contoh.filmapp.ui.adapter.FeaturedMovieAdapter
import com.contoh.filmapp.ui.adapter.MovieAdapter
import com.contoh.filmapp.viewmodel.MovieViewModel
import com.contoh.filmapp.viewmodel.MovieViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels {
        MovieViewModelFactory("FilmApp")
    }

    private lateinit var featuredAdapter: FeaturedMovieAdapter
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFeaturedRecyclerView()
        setupMoviesRecyclerView()
        observeViewModel()
    }

    private fun setupFeaturedRecyclerView() {
        featuredAdapter = FeaturedMovieAdapter()
        binding.rvFeatured.apply {
            adapter = featuredAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun setupMoviesRecyclerView() {
        movieAdapter = MovieAdapter(
            onImdbClick = { movie -> viewModel.onImdbClick(movie) },
            onDetailClick = { movie -> viewModel.onDetailClick(movie) }
        )
        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.featuredMovies.collect { featuredMovies ->
                        featuredAdapter.submitList(featuredMovies)
                    }
                }

                launch {
                    viewModel.movies.collect { movies ->
                        movieAdapter.submitList(movies)
                    }
                }

                launch {
                    viewModel.navigationEvent.collect { event ->
                        when (event) {
                            is MovieViewModel.NavigationEvent.NavigateToDetail -> {
                                val bundle = Bundle().apply {
                                    putInt("movieId", event.movieId)
                                }
                                findNavController().navigate(
                                    R.id.action_home_to_detail,
                                    bundle
                                )
                                viewModel.resetNavigationEvent()
                            }
                            is MovieViewModel.NavigationEvent.OpenImdb -> {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    event.url.toUri()
                                )
                                startActivity(intent)
                                viewModel.resetNavigationEvent()
                            }
                            is MovieViewModel.NavigationEvent.None -> {}
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}