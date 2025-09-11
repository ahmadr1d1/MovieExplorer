package com.ahmadrd.movieexplorer.ui.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadrd.movieexplorer.core.R.string
import com.ahmadrd.movieexplorer.R
import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.ui.ListCastingMovieAdapter
import com.ahmadrd.movieexplorer.core.ui.ListSimilarMoviesAdapter
import com.ahmadrd.movieexplorer.core.utils.DataMapper.toAllMovie
import com.ahmadrd.movieexplorer.core.utils.FormatTime
import com.ahmadrd.movieexplorer.core.utils.TMDBImage
import com.ahmadrd.movieexplorer.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.ahmadrd.movieexplorer.core.R.color
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

        binding.viewErrorDetail.btnRetry.setOnClickListener {
            viewModel.retry()
        }

        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        Log.i("DetailActivity", "Movie id received: $movieId")
        if (movieId == -1 || movieId == 0) { // Check for invalid IDs
            showLongToast(getString(string.movie_id_empty))
            finish()
            return
        }
        viewModel.setMovieId(movieId)

        observeDetailMovie()
        observeCasting()
        observeSimilarMovies()
        observeFavoriteStatus()
    }

    private fun setupRecyclerView() {
        with(binding.rvCast) {
            adapter = castingAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        with(binding.rvSimilarMovies) {
            adapter = similarMoviesAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeFavoriteStatus() {
        // Observer for favorite status (UI updates only)
        viewModel.isFavorite.observe(this) { isFav ->
            Log.i("DetailActivity", "Favorite status changed: $isFav")
            updateFavoriteUI(isFav)
        }

        // Observer for toast message
        viewModel.toastMessage.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                // Clear message after showing up
                viewModel.clearToastMessage()
            }
        }
    }

    private fun updateFavoriteUI(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.iconTint = ColorStateList.valueOf(
                ContextCompat.getColor(this, color.movie_red)
            )
        } else {
            binding.fabFavorite.iconTint = ColorStateList.valueOf(
                ContextCompat.getColor(this, color.movie_white)
            )
        }
    }

    private fun observeDetailMovie() {
        viewModel.detailMovie.observe(this) { result ->
            when (result) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    showLoading(false)
                    result.data?.let { detailMovie ->
                        populateDetailMovieUi(detailMovie)
                        setupRecyclerView()
                        // Set OnClickListener favorite button here, ensuring detailMovie is available
                        binding.fabFavorite.setOnClickListener {
                            val allMovie = detailMovie.toAllMovie()
                            if (allMovie != null) {
                                viewModel.toggleFavorite(allMovie)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Cannot favorite: Movie data is invalid",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.w(
                                    "DetailActivity",
                                    "Attempted to favorite movie with invalid ID: ${detailMovie.id} - ${detailMovie.title}"
                                )
                            }
                        }
                    } ?: run {
                        showLoading(false)
                        showError("Detail data is null")
                        Log.e("DetailActivity", "State: Success, but data is null")
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    showError(result.message)
                    Log.e("DetailActivity", "State: Error, Message: ${result.message}")
                }
            }
        }
    }

    private fun populateDetailMovieUi(data: DetailMovie) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(TMDBImage.BASE_IMAGE_URL + data.posterPath)
                .error(com.ahmadrd.movieexplorer.core.R.drawable.baseline_broken_image_24)
                .into(ivPoster)
            tvMovieTitle.text = data.title
            tvReleaseYear.text = FormatTime.formatRelativeTimeFromDate(data.releaseDate)
            tvDuration.text = data.runtime.toString()
            tvOverview.text = data.overview
            tvLanguage.text = data.originalLanguage
            genreDetailMovie.text = data.genres?.joinToString { it.name ?: "no genres" }
            ratingDetail.text = formatRating(data.voteAverage)
        }
    }

    private fun observeCasting() {
        viewModel.castingMovie.observe(this) { result ->
            when (result) {
                is Resource.Loading -> showLoading(true) // Consider separate loading for casting
                is Resource.Success -> {
                    showLoading(false)
                    if (result.data.isNullOrEmpty()) {
                        showLoading(false)
                        showLongToast("Not found casting members for this movie")
                        Log.w(
                            "DetailActivity",
                            "Casting members movie Error: Data is null or empty"
                        )
                    } else {
                        castingAdapter.submitList(result.data)
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    // showError(result.message) // Consider specific error handling for casting
                    Log.e("DetailActivity", "Casting Error: ${result.message}")
                }
            }
        }
    }

    private fun observeSimilarMovies() {
        viewModel.similarMovies.observe(this) { result ->
            when (result) {
                is Resource.Loading -> showLoading(true) // Consider separate loading for similar movies
                is Resource.Success -> {
                    showLoading(false)
                    if (result.data.isNullOrEmpty()) {
                        showLoading(false)
                        showLongToast("Not found similar movies for this movie")
                        Log.w(
                            "DetailActivity",
                            "Similar Movies Error: Data is null or empty"
                        )
                    } else {
                        similarMoviesAdapter.submitList(result.data)
                    }
                }

                is Resource.Error -> {
                    showLoading(false)
                    showError(result.message) // Consider specific error handling for similar movies
                    Log.e("DetailActivity", "Similar Movies Error: ${result.message}")
                }
            }
        }
    }

    private fun formatRating(rating: Double?): String {
        val rounded = rating?.let { round(it * 10) / 10 }
        return rounded.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.imageStars.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        if (isLoading) binding.viewErrorDetail.root.visibility = View.GONE

    }

    private fun showLongToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showError(message: String?) {
        binding.viewErrorDetail.root.visibility = View.VISIBLE
        binding.viewErrorDetail.tvErrorMessage.text = message ?: "Something went wrong"
        binding.imageStars.visibility = View.INVISIBLE // Hide stars on error
        binding.progressBarDetail.visibility = View.GONE
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}