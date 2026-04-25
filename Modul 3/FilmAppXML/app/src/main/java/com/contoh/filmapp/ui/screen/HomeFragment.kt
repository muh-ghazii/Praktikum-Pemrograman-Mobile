package com.contoh.filmapp.ui.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.contoh.filmapp.R
import com.contoh.filmapp.ui.adapter.FeaturedMovieAdapter
import com.contoh.filmapp.ui.adapter.MovieAdapter
import com.contoh.filmapp.data.MovieData
import com.contoh.filmapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun setupFeaturedRecyclerView() {
        val featuredAdapter = FeaturedMovieAdapter(MovieData.featuredMovies)

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
        val movieAdapter = MovieAdapter(
            movies = MovieData.movies,
            onImdbClick = { movie ->
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    movie.imdbUrl.toUri()
                )
                startActivity(intent)
            },
            onDetailClick = { movie ->
                val bundle = Bundle().apply {
                    putInt("movieId", movie.id)
                }
                findNavController().navigate(
                    R.id.action_home_to_detail,
                    bundle
                )
            }
        )

        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}