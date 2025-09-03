package com.ahmadrd.movieexplorer.ui.detail

import android.os.Bundle
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
import com.ahmadrd.movieexplorer.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var binding: ActivityDetailBinding

    private val castingAdapter = ListCastingMovieAdapter()

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

        setupCastingRecycler()

        // Ambil movieId dari Intent
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        if (movieId == -1) {
            Toast.makeText(this, "Movie ID tidak valid", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Set ke ViewModel → akan memicu fetch via switchMap
        viewModel.setMovieId(movieId)

        observeCasting()
    }

    private fun setupCastingRecycler() = with(binding.rvCast) {
        adapter = castingAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(
            this@DetailActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        // optional: dekorasi jarak
        // addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.space_8)))
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
                    // Tampilkan empty state kalau perlu
//                    binding.viewErrorDetail.isVisible = data.isEmpty()
//                    binding.viewErrorDetail.text = if (data.isEmpty())
//                        "Belum ada data pemain."
//                    else
//                        ""
                }

                is Resource.Error -> {
                    showLoading(false)
                    binding.viewErrorDetail.root.visibility = View.VISIBLE
                    binding.viewErrorDetail.tvErrorMessage.text =
                        result.message ?: "Terjadi kesalahan"
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }
}
