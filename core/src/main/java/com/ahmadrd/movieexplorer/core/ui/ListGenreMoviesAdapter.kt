package com.ahmadrd.movieexplorer.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrd.movieexplorer.core.databinding.ItemGenresBinding
import com.ahmadrd.movieexplorer.core.domain.model.GenresMovie

class ListGenreMoviesAdapter :
    ListAdapter<GenresMovie, ListGenreMoviesAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((GenresMovie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemGenresBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GenresMovie) {
            binding.btnGenresName.text = data.name
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<GenresMovie> =
            object : DiffUtil.ItemCallback<GenresMovie>() {
                override fun areItemsTheSame(
                    oldItem: GenresMovie,
                    newItem: GenresMovie
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: GenresMovie,
                    newItem: GenresMovie
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}