package com.ahmadrd.movieexplorer.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.movieexplorer.R
import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.ui.ListCastingMovieAdapter
import com.ahmadrd.movieexplorer.core.ui.ListSimilarMoviesAdapter
import com.ahmadrd.movieexplorer.core.utils.TMDBImage
import com.ahmadrd.movieexplorer.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var binding: ActivityDetailBinding

    private val castingAdapter = ListCastingMovieAdapter()

    private val similarMoviesAdapter = ListSimilarMoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        // Get movieId from Intent
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        if (movieId == -1) {
            Toast.makeText(
                this,
                "Movie id is not found",
                Toast.LENGTH_SHORT
            ).show()
            finish()
            return
        }

        // Set id to viewModel, it will trigger fetch via switchMap
        viewModel.setMovieId(movieId)

        observeDetailMovie()
        observeCasting()
        observeSimilarMovies()
    }

    private fun setupRecyclerView() {

        // Casting Movie
        with(binding.rvCast) {
            adapter = castingAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@DetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        // Similar Movies
        with(binding.rvSimilarMovies) {
            adapter = similarMoviesAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                this@DetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun observeDetailMovie() {
        viewModel.detailMovie.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data
                    if (data != null) {
                        with(binding) {
                            Glide.with(this@DetailActivity)
                                .load(TMDBImage.BASE_IMAGE_URL + data.posterPath)
                                .error(com.ahmadrd.movieexplorer.core.R.drawable.baseline_broken_image_24)
                                .into(binding.ivPoster)
                            tvMovieTitle.text = data.title
                            tvReleaseYear.text = data.releaseDate
                            tvDuration.text = data.runtime.toString()
                            tvOverview.text = data.overview
                            tvLanguage.text = data.originalLanguage
                            genreDetailMovie.text = data.genres?.joinToString { it.name ?: "empty" }
                            ratingDetail.text = formatRating(data.voteAverage)
                        }
                        setupRecyclerView()
                    } else {
                        Toast.makeText(
                            this,
                            "Data is null",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    Log.e("DetailActivity", "State: Error, Message: ${result.message}")
                    binding.viewErrorDetail.root.visibility = View.VISIBLE
                    binding.viewErrorDetail.tvErrorMessage.text =
                        result.message ?: "Something went wrong"
                }
            }
        }
    }

    private fun observeCasting() {
        viewModel.castingMovie.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data.orEmpty()
                    castingAdapter.submitList(data)
                }

                is Resource.Error -> {
                    showLoading(false)
                    binding.viewErrorDetail.root.visibility = View.VISIBLE
                    binding.viewErrorDetail.tvErrorMessage.text =
                        result.message ?: "Something went wrong"
                }
            }
        }
    }

    private fun observeSimilarMovies() {
        viewModel.similarMovies.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    showLoading(true)
                }

                is Resource.Success -> {
                    showLoading(false)
                    val data = result.data.orEmpty()
                    similarMoviesAdapter.submitList(data)
                }

                is Resource.Error -> {
                    showLoading(false)
                    binding.viewErrorDetail.root.visibility = View.VISIBLE
                    binding.viewErrorDetail.tvErrorMessage.text =
                        result.message ?: "Something went wrong"
                }
            }
        }
    }

    private fun formatRating(rating: Double?): String {
        val rounded = rating?.let { round(it * 10) / 10 }
        return rounded.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}
