package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable

class PosterImage(
        val tiny: String,
        val small: String,
        val medium: String,
        val large: String,
        val original: String
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tiny)
        parcel.writeString(small)
        parcel.writeString(medium)
        parcel.writeString(large)
        parcel.writeString(original)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PosterImage> {
        override fun createFromParcel(parcel: Parcel): PosterImage {
            return PosterImage(parcel)
        }

        override fun newArray(size: Int): Array<PosterImage?> {
            return arrayOfNulls(size)
        }
    }
}