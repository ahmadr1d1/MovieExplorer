package com.ahmadrd.movieexplorer.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.movieexplorer.core.R
import com.ahmadrd.movieexplorer.core.databinding.ItemSimilarMovieBinding
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.utils.TMDBImage
import com.bumptech.glide.Glide
import kotlin.math.round

class ListSimilarMoviesAdapter :
    ListAdapter<SimilarMovies, ListSimilarMoviesAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((SimilarMovies) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemSimilarMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemSimilarMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SimilarMovies) {
            Glide.with(itemView.context)
                .load(TMDBImage.BASE_IMAGE_URL + data.posterPath)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.similarMoviesImage)
            binding.similarMoviesTitle.text = data.title
            binding.similarMoviesReleaseDate.text = data.releaseDate
            binding.ratingSimilarMovies.text = formatRating(data.voteAverage ?: -0.0)
            binding.genreSimilarMovies.text = data.genreNames?.joinToString(", ")
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    private fun formatRating(rating: Double): String {
        val rounded = round(rating * 10) / 10
        return rounded.toString()
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<SimilarMovies> =
            object : DiffUtil.ItemCallback<SimilarMovies>() {
                override fun areItemsTheSame(
                    oldItem: SimilarMovies,
                    newItem: SimilarMovies
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: SimilarMovies,
                    newItem: SimilarMovies
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}