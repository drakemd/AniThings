package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable

data class CoverImage(
        val tiny: String,
        val small: String,
        val large: String,
        val original: String
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tiny)
        parcel.writeString(small)
        parcel.writeString(large)
        parcel.writeString(original)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoverImage> {
        override fun createFromParcel(parcel: Parcel): CoverImage {
            return CoverImage(parcel)
        }

        override fun newArray(size: Int): Array<CoverImage?> {
            return arrayOfNulls(size)
        }
    }
}