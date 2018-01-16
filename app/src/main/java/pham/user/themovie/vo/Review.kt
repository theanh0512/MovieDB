package pham.user.themovie.vo

import android.os.Parcel
import android.os.Parcelable

class Review : Parcelable {
    var id: String
        internal set
    var author: String
        internal set
    var content: String
        internal set

    constructor(id: String, author: String, content: String) {
        this.id = id
        this.author = author
        this.content = content
    }

    protected constructor(parcel: Parcel) {
        id = parcel.readString()
        author = parcel.readString()
        content = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(author)
        dest.writeString(content)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Review> = object : Parcelable.Creator<Review> {
            override fun createFromParcel(`in`: Parcel): Review {
                return Review(`in`)
            }

            override fun newArray(size: Int): Array<Review?> {
                return arrayOfNulls(size)
            }
        }
    }
}
