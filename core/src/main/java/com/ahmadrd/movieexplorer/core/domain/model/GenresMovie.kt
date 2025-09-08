package com.ahmadrd.movieexplorer.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenresMovie(
    val id: Int,
    val name: String? = null
) : Parcelable
