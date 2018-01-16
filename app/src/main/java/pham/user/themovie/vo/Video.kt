package pham.user.themovie.vo

import android.os.Parcel
import android.os.Parcelable

class Video : Parcelable {
    var id: String
        internal set
    var iso_639_1: String
        internal set
    var key: String
        internal set
    var name: String
        internal set
    var site: String
        internal set
    var size: Int = 0
        internal set
    var type: String
        internal set

    constructor(id: String, iso_639_1: String, key: String, name: String, site: String, size: Int, type: String) {
        this.id = id
        this.iso_639_1 = iso_639_1
        this.key = key
        this.name = name
        this.site = site
        this.size = size
        this.type = type
    }

    protected constructor(parcel: Parcel) {
        id = parcel.readString()
        iso_639_1 = parcel.readString()
        key = parcel.readString()
        name = parcel.readString()
        site = parcel.readString()
        size = parcel.readInt()
        type = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(iso_639_1)
        dest.writeString(key)
        dest.writeString(name)
        dest.writeString(site)
        dest.writeInt(size)
        dest.writeString(type)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Video> = object : Parcelable.Creator<Video> {
            override fun createFromParcel(`in`: Parcel): Video {
                return Video(`in`)
            }

            override fun newArray(size: Int): Array<Video?> {
                return arrayOfNulls(size)
            }
        }
    }
}
