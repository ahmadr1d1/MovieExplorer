package com.ahmadrd.movieexplorer.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.movieexplorer.core.R
import com.ahmadrd.movieexplorer.core.databinding.ItemTrendingMoviesBinding
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies
import com.ahmadrd.movieexplorer.core.utils.TMDBImage
import com.bumptech.glide.Glide
import kotlin.math.round

class ListTrendingMoviesAdapter :
    ListAdapter<TrendingMovies, ListTrendingMoviesAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((TrendingMovies) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemTrendingMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemTrendingMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TrendingMovies) {
            Glide.with(itemView.context)
                .load(TMDBImage.BASE_IMAGE_URL + data.posterPath)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.trendingMoviesImage)
            binding.titleTrendingMovies.text = data.title
            binding.releaseDateTrendingMovies.text = data.releaseDate
            binding.ratingTrendingMovies.text = formatRating(data.voteAverage)
            binding.genreTrendingMovies.text = data.genreIds.toString()
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
        val DIFF_CALLBACK: DiffUtil.ItemCallback<TrendingMovies> =
            object : DiffUtil.ItemCallback<TrendingMovies>() {
                override fun areItemsTheSame(
                    oldItem: TrendingMovies,
                    newItem: TrendingMovies
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: TrendingMovies,
                    newItem: TrendingMovies
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}