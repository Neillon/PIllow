package com.example.presentation.binding

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class MovieBinding(
    var id: Long?,
    val video: Boolean,
    val voteCount: Int,
    val voteAverage: Double,
    val title: String,
    val releaseDate: Date?,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val backdropPath: String?,
    var homepage: String? = "",
    val adult: Boolean,
    val overview: String,
    val posterPath: String?,
    val popularity: Double,
    val mediaType: String,
    var favorite: Boolean = false,
    var movieId: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        TODO("releaseDate"),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        TODO("genreIds"),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeInt(voteCount)
        parcel.writeDouble(voteAverage)
        parcel.writeString(title)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeString(backdropPath)
        parcel.writeString(homepage)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(overview)
        parcel.writeString(posterPath)
        parcel.writeDouble(popularity)
        parcel.writeString(mediaType)
        parcel.writeByte(if (favorite) 1 else 0)
        parcel.writeLong(movieId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieBinding> {
        override fun createFromParcel(parcel: Parcel): MovieBinding {
            return MovieBinding(parcel)
        }

        override fun newArray(size: Int): Array<MovieBinding?> {
            return arrayOfNulls(size)
        }
    }
}