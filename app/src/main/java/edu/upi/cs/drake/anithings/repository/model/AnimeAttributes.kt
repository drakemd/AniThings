package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable

data class AnimeAttributes(
        val canonicalTitle: String,
        val titles: Titles,
        val synopsis: String,
        val averageRating: String,
        val startDate: String,
        val endDate: String,
        val popularityRank: Int,
        val ratingRank: Int?,
        val ageRating: String,
        val ageRatingGuide: String,
        val subtype: String,
        val status: String,
        val tba: String?,
        val posterImage: PosterImage,
        val coverImage: CoverImage,
        val episodeCount: Int?,
        val episodeLength: Int?,
        val youtubeVideoId: String,
        val showType: String,
        val nsfw: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()?: "n/a",
            parcel.readParcelable(Titles::class.java.classLoader),
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readInt(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readParcelable(PosterImage::class.java.classLoader),
            parcel.readParcelable(CoverImage::class.java.classLoader)?: CoverImage("","","",""),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString()?: "n/a",
            parcel.readString()?: "n/a",
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(canonicalTitle)
        parcel.writeParcelable(titles, flags)
        parcel.writeString(synopsis)
        parcel.writeString(averageRating)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeInt(popularityRank)
        parcel.writeValue(ratingRank)
        parcel.writeString(ageRating)
        parcel.writeString(ageRatingGuide)
        parcel.writeString(subtype)
        parcel.writeString(status)
        parcel.writeString(tba)
        parcel.writeParcelable(posterImage, flags)
        parcel.writeParcelable(coverImage, flags)
        parcel.writeValue(episodeCount)
        parcel.writeValue(episodeLength)
        parcel.writeString(youtubeVideoId)
        parcel.writeString(showType)
        parcel.writeByte(if (nsfw) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnimeAttributes> {
        override fun createFromParcel(parcel: Parcel): AnimeAttributes {
            return AnimeAttributes(parcel)
        }

        override fun newArray(size: Int): Array<AnimeAttributes?> {
            return arrayOfNulls(size)
        }
    }
}