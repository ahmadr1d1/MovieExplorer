package com.ahmadrd.movieexplorer.core.utils.database

import android.os.Parcel
import com.ahmadrd.movieexplorer.core.data.source.remote.response.GenresItem
import kotlinx.parcelize.Parceler

object GenresItemParceler : Parceler<GenresItem> {
    override fun create(parcel: Parcel): GenresItem {
        val id = parcel.readInt()
        val name = parcel.readString() ?: ""
        return GenresItem(name, id)
    }

    override fun GenresItem.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(id ?: 0)
        parcel.writeString(name) // Method writeString is nullable
    }
}