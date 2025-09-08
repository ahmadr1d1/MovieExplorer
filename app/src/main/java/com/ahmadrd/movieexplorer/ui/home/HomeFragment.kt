package com.ahmadrd.movieexplorer.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.movieexplorer.R
import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.ui.ListGenreMoviesAdapter
import com.ahmadrd.movieexplorer.core.ui.ListPopularMoviesAdapter
import com.ahmadrd.movieexplorer.core.ui.ListTrendingMoviesAdapter
import com.ahmadrd.movieexplorer.databinding.FragmentHomeBinding
import com.ahmadrd.movieexplorer.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val popularMoviesAdapter = ListPopularMoviesAdapter()
    private val trendingMoviesAdapter = ListTrendingMoviesAdapter()
    private val genresMovieAdapter = ListGenreMoviesAdapter()

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
        setupRecyclerView()
        observePopularMovies()
        observeTrendingMovies()
        observeGenresMovie()

        with(binding) {
            etSearch.setOnClickListener {
                showToastFeatureNotAvailable()
            }
            btnSwitchGenreMovies.setOnClickListener {
                showToastFeatureNotAvailable()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvPopularMovies) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = popularMoviesAdapter
        }

        with(binding.rvTrendingMovies) {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = trendingMoviesAdapter
        }

        with(binding.rvGenresMovies) {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = genresMovieAdapter
        }
    }

    private fun observePopularMovies() {
        if (activity != null) {

            popularMoviesAdapter.onItemClick = { clickedMovie ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, clickedMovie.id)
                startActivity(intent)
            }

            homeViewModel.popularMovies.observe(viewLifecycleOwner) { popularMovies ->
                if (popularMovies != null) {
                    when (popularMovies) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> {
                            showLoading(false)
                            popularMoviesAdapter.submitList(popularMovies.data)
                        }

                        is Resource.Error -> {
                            showLoading(false)
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvErrorMessage.text =
                                popularMovies.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    private fun observeTrendingMovies() {
        if (activity != null) {
            trendingMoviesAdapter.onItemClick = { clickedMovie ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, clickedMovie.id)
                startActivity(intent)
            }

            homeViewModel.trendingMovies.observe(viewLifecycleOwner) { trendingMovies ->
                if (trendingMovies != null) {
                    when (trendingMovies) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> {
                            showLoading(false)
                            trendingMoviesAdapter.submitList(trendingMovies.data)
                        }

                        is Resource.Error -> {
                            showLoading(false)
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvErrorMessage.text =
                                trendingMovies.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    private fun observeGenresMovie() {
        if (activity != null) {

            genresMovieAdapter.onItemClick = {
                showToastFeatureNotAvailable()
            }

            homeViewModel.genresMovie.observe(viewLifecycleOwner) { genresMovie ->
                if (genresMovie != null) {
                    when (genresMovie) {
                        is Resource.Loading -> showLoading(true)
                        is Resource.Success -> {
                            showLoading(false)
                            genresMovieAdapter.submitList(genresMovie.data)
                        }

                        is Resource.Error -> {
                            showLoading(false)
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvErrorMessage.text =
                                genresMovie.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToastFeatureNotAvailable() {
        Toast.makeText(
            context,
            "This feature is not available yet",
            Toast.LENGTH_SHORT
        )
            .show()
    }

}