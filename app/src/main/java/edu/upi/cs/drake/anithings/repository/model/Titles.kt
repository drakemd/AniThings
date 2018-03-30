package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable

data class Titles(
        val en: String,
        val en_jp: String,
        val ja_jp: String
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(en)
        parcel.writeString(en_jp)
        parcel.writeString(ja_jp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Titles> {
        override fun createFromParcel(parcel: Parcel): Titles {
            return Titles(parcel)
        }

        override fun newArray(size: Int): Array<Titles?> {
            return arrayOfNulls(size)
        }
    }
}