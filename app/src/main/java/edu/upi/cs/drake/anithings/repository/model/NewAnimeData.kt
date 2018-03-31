package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.ViewType
import edu.upi.cs.drake.anithings.common.extensions.createIntList
import edu.upi.cs.drake.anithings.common.extensions.writeIntList

data class NewAnimeData(
        var id: Int,
        var canonicalTitle: String,
        var synopsis: String,
        var averageRating: String?,
        var startDate: String?,
        var endDate: String?,
        var popularityRank: Int,
        var ratingRank: Int?,
        var ageRating: String?,
        var ageRatingGuide: String?,
        var status: String,
        var genres: List<Int>,
        var posterImage: String,
        var coverImage: String?,
        var youtubeVideoId: String?,
        var showType: String
): ViewType, Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createIntList(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun getViewType(): Int {
        return AdapterConstants.ANIME
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(canonicalTitle)
        parcel.writeString(synopsis)
        parcel.writeString(averageRating)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeInt(popularityRank)
        parcel.writeValue(ratingRank)
        parcel.writeString(ageRating)
        parcel.writeString(ageRatingGuide)
        parcel.writeString(status)
        parcel.writeIntList(genres)
        parcel.writeString(posterImage)
        parcel.writeString(coverImage)
        parcel.writeString(youtubeVideoId)
        parcel.writeString(showType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewAnimeData> {
        override fun createFromParcel(parcel: Parcel): NewAnimeData {
            return NewAnimeData(parcel)
        }

        override fun newArray(size: Int): Array<NewAnimeData?> {
            return arrayOfNulls(size)
        }
    }
}