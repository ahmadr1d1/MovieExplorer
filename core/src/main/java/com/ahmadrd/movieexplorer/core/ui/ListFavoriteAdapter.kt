package com.ahmadrd.movieexplorer.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.movieexplorer.core.R
import com.ahmadrd.movieexplorer.core.databinding.ItemFavoriteMoviesBinding
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.utils.FormatTime
import com.ahmadrd.movieexplorer.core.utils.TMDBImage
import com.bumptech.glide.Glide
import kotlin.math.round

class ListFavoriteAdapter :
    ListAdapter<AllMovie, ListFavoriteAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((AllMovie) -> Unit)? = null
    var onRemoveClick: ((AllMovie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemFavoriteMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemFavoriteMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AllMovie) {
            Glide.with(itemView.context)
                .load(TMDBImage.BASE_IMAGE_URL + data.posterPath)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.ivFavoritePoster)
            with(binding) {
                tvFavoriteTitle.text = data.title
                tvReleaseDate.text = FormatTime.formatRelativeTimeFromDate(data.releaseDate)
                tvFavoriteDuration.text = data.runtime.toString()
                tvFavoriteLanguage.text = data.originalLanguage
                tvFavoriteRating.text = formatRating(data.voteAverage ?: -0.0)
                genreFavoriteMovie.text = data.genreNames?.joinToString(", ")
                tvFavoriteOverview.text = data.overview
                tvAddedDateFormat.text = FormatTime.formatRelativeTime(data.dateAdded)
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
            binding.btnRemove.setOnClickListener {
                onRemoveClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    private fun formatRating(rating: Double): String {
        val rounded = round(rating * 10) / 10
        return rounded.toString()
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<AllMovie> =
            object : DiffUtil.ItemCallback<AllMovie>() {
                override fun areItemsTheSame(
                    oldItem: AllMovie,
                    newItem: AllMovie
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: AllMovie,
                    newItem: AllMovie
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}