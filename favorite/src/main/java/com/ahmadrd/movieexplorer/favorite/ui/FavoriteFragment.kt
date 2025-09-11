package com.ahmadrd.movieexplorer.favorite.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.movieexplorer.core.ui.ListFavoriteAdapter
import com.ahmadrd.movieexplorer.core.utils.favorites.FavoriteState
import com.ahmadrd.movieexplorer.core.utils.favorites.FavoritesViewState
import com.ahmadrd.movieexplorer.di.FavoriteModuleDependencies
import com.ahmadrd.movieexplorer.favorite.R
import com.ahmadrd.movieexplorer.favorite.di.FavoriteViewModelFactory
import com.ahmadrd.movieexplorer.favorite.databinding.FragmentFavoriteBinding
import com.ahmadrd.movieexplorer.favorite.di.DaggerFavoriteComponent
import com.ahmadrd.movieexplorer.ui.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import com.ahmadrd.movieexplorer.core.R.style
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private lateinit var listFavoriteAdapter: ListFavoriteAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireActivity().applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeFavoriteMovies()

        binding.btnExploreMovies.setOnClickListener {
            findNavController().navigate(com.ahmadrd.movieexplorer.R.id.navigation_home)
        }

        binding.btnSort.setOnClickListener {
            showToast("This feature is not available yet")
        }
    }

    private fun setupRecyclerView() {
        listFavoriteAdapter = ListFavoriteAdapter()
        listFavoriteAdapter.onItemClick = { selectedMovie ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.Companion.EXTRA_MOVIE_ID, selectedMovie.id)
            startActivity(intent)
        }
        listFavoriteAdapter.onRemoveClick = { selectedMovie ->
            AlertDialog.Builder(
                requireContext(),
                style.CustomAlertDialog
            ).apply {
                setTitle("Remove Item")
                setMessage("Are you sure want to remove this item?")
                setCancelable(true)
                setPositiveButton("Yes") { _, _ ->
                    favoriteViewModel.removeMovieFromFavorite(selectedMovie)
                    showToast("Success removed item")
                }
                create()
                show()
            }

        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listFavoriteAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeFavoriteMovies() {
        favoriteViewModel.favoriteMovies.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoriteState.Loading -> {
                    updateFavoriteUi(FavoritesViewState.LOADING)
                }

                is FavoriteState.Success -> {
                    updateFavoriteUi(FavoritesViewState.SUCCESS)

                    listFavoriteAdapter.submitList(state.data)

                    val movieCount = state.data.size
                    binding.tvFavoriteCount.text = resources.getQuantityString(
                        R.plurals.favorite_movie_count,
                        movieCount,
                        movieCount
                    )
                }

                is FavoriteState.Empty -> {
                    updateFavoriteUi(FavoritesViewState.EMPTY)
                    binding.tvFavoriteCount.text = getString(R.string.empty_movies_count)
                }

                is FavoriteState.Error -> {
                    updateFavoriteUi(FavoritesViewState.ERROR)
                    showToast(state.message)
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun updateFavoriteUi(state: FavoritesViewState) {
        when (state) {
            FavoritesViewState.LOADING -> {
                binding.progressBarFavorite.isVisible = true
                binding.rvFavorites.isVisible = false
                binding.llEmptyState.isVisible = false
                binding.tvFavoriteCount.isVisible = false
            }

            FavoritesViewState.SUCCESS -> {
                binding.progressBarFavorite.isVisible = false
                binding.rvFavorites.isVisible = true
                binding.llEmptyState.isVisible = false
                binding.tvFavoriteCount.isVisible = true
            }

            FavoritesViewState.EMPTY -> {
                binding.progressBarFavorite.isVisible = false
                binding.rvFavorites.isVisible = false
                binding.llEmptyState.isVisible = true
                binding.tvFavoriteCount.isVisible = true
            }

            FavoritesViewState.ERROR -> {
                binding.progressBarFavorite.isVisible = false
                binding.rvFavorites.isVisible = false
                binding.llEmptyState.isVisible = false
                binding.tvFavoriteCount.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorites.adapter = null // Clear adapter to avoid memory leaks
        _binding = null
    }
}