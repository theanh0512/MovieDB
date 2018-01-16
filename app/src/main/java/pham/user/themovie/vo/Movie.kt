package pham.user.themovie.vo

import android.os.Parcel
import android.os.Parcelable

class Movie(val id: Int, var isAdult: Boolean?,
            var backdrop_path: String?,
            var genre_ids: IntArray?,
            var original_language: String?,
            var original_title: String?,
            var overview: String?,
            var release_date: String?,
            var poster_path: String?,
            var popularity: Double?,
            var title: String?,
            var video: Boolean?,
            var vote_average: Double?,
            var vote_count: Int?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readString(),
            parcel.createIntArray(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeValue(isAdult)
        dest.writeString(backdrop_path)
        dest.writeIntArray(genre_ids)
        dest.writeString(original_language)
        dest.writeString(original_title)
        dest.writeString(overview)
        dest.writeString(release_date)
        dest.writeString(poster_path)
        dest.writeValue(popularity)
        dest.writeString(title)
        dest.writeValue(video)
        dest.writeValue(vote_average)
        dest.writeValue(vote_count)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(`in`: Parcel): Movie {
                return Movie(`in`)
            }

            override fun newArray(size: Int): Array<Movie?> {
                return arrayOfNulls(size)
            }
        }
    }
}
