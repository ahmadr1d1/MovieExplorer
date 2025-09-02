package com.ahmadrd.movieexplorer.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.movieexplorer.core.R
import com.ahmadrd.movieexplorer.core.databinding.ItemPopularMoviesBinding
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.utils.TMDBImage
import com.bumptech.glide.Glide
import kotlin.math.round

class ListPopularMoviesAdapter :
    ListAdapter<PopularMovies, ListPopularMoviesAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((PopularMovies) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemPopularMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemPopularMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PopularMovies) {
            Glide.with(itemView.context)
                .load(TMDBImage.BASE_IMAGE_URL + data.posterPath)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.popularMoviesImage)
            binding.popularMoviesTitle.text = data.title
            binding.popularMoviesReleaseDate.text = data.releaseDate
            binding.ratingPopularMovies.text = formatRating(data.voteAverage)
            binding.genrePopularMovies.text = data.genreNames.joinToString(", ")
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
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PopularMovies> =
            object : DiffUtil.ItemCallback<PopularMovies>() {
                override fun areItemsTheSame(
                    oldItem: PopularMovies,
                    newItem: PopularMovies
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: PopularMovies,
                    newItem: PopularMovies
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}