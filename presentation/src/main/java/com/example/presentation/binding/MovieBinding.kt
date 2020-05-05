package com.example.presentation.binding

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.presentation.BR
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class MovieBinding : BaseObservable(), Parcelable {
    var id: Long? = null
    var video: Boolean = false
    var voteCount: Int = 0
    var voteAverage: Double = 0.0
    var title: String = ""
    var releaseDate: Date? = Date()
    var originalLanguage: String = ""
    var originalTitle: String = ""
    var genreIds: List<Int> = listOf()
    var backdropPath: String? = ""
    var homepage: String? = ""
    var adult: Boolean = false
    var overview: String = ""
    var posterPath: String? = ""
    var popularity: Double = 0.0
    var mediaType: String = ""

    @get:Bindable
    var favorite: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.favorite)
        }

    var movieId: Long = 0L
}
