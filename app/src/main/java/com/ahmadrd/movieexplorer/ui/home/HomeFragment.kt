package com.ahmadrd.movieexplorer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.movieexplorer.R
import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.ui.ListPopularMoviesAdapter
import com.ahmadrd.movieexplorer.core.ui.ListTrendingMoviesAdapter
import com.ahmadrd.movieexplorer.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

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
        observePopularMovies()
        observeTrendingMovies()
    }

    private fun observePopularMovies() {
        if (activity != null) {

            val popularMoviesAdapter = ListPopularMoviesAdapter()
//            popularMoviesAdapter.onItemClick = { selectedData ->
//                val intent = Intent(activity, DetailTourismActivity::class.java)
//                intent.putExtra(DetailTourismActivity.EXTRA_DATA, selectedData)
//                startActivity(intent)
//            }

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

            with(binding.rvPopularMovies) {
                layoutManager = GridLayoutManager(context, 2)
                adapter = popularMoviesAdapter
            }
        }
    }

    private fun observeTrendingMovies() {
        if (activity != null) {

            val trendingMoviesAdapter = ListTrendingMoviesAdapter()
//            trendingMoviesAdapter.onItemClick = { selectedData ->
//                val intent = Intent(activity, DetailTourismActivity::class.java)
//                intent.putExtra(DetailTourismActivity.EXTRA_DATA, selectedData)
//                startActivity(intent)
//            }

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

            with(binding.rvTrendingMovies) {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = trendingMoviesAdapter
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

}