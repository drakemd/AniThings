package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.ViewType

data class AnimeData(
        val id: Int,
        val type: String,
        val attributes: AnimeAttributes
): ViewType, Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readParcelable(AnimeAttributes::class.java.classLoader))

    override fun getViewType() = AdapterConstants.ANIME
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(type)
        parcel.writeParcelable(attributes, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeData> {
        override fun createFromParcel(parcel: Parcel): AnimeData {
            return AnimeData(parcel)
        }

        override fun newArray(size: Int): Array<AnimeData?> {
            return arrayOfNulls(size)
        }
    }
}