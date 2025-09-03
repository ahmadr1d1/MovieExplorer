package com.ahmadrd.movieexplorer.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.movieexplorer.core.R
import com.ahmadrd.movieexplorer.core.databinding.ItemCastMemberBinding
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.utils.TMDBImage
import com.bumptech.glide.Glide

class ListCastingMovieAdapter :
    ListAdapter<CastingMovie, ListCastingMovieAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemCastMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemCastMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CastingMovie) {
            Glide.with(binding.root.context)
                .load(TMDBImage.BASE_IMAGE_URL + data.profilePath)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.imageCast)
            with(binding) {
                tvCastName.text = data.name
                tvCastCharacter.text = data.character
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<CastingMovie> =
            object : DiffUtil.ItemCallback<CastingMovie>() {
                override fun areItemsTheSame(
                    oldItem: CastingMovie,
                    newItem: CastingMovie
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: CastingMovie,
                    newItem: CastingMovie
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}