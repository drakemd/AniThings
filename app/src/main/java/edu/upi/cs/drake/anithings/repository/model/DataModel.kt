package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.ViewType
import edu.upi.cs.drake.anithings.common.extensions.createParcel

/**
 * Created by drake on 3/27/2018.
 *
 */

data class ListAnime(val data: List<AnimeData>): Parcelable{

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{ListAnime(it)}
    }

    private constructor(parcelIn: Parcel): this(
            mutableListOf<AnimeData>().apply {
                parcelIn.readTypedList(this, AnimeData.CREATOR)
            }
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeTypedList(data)
    }

    override fun describeContents() = 0
}

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

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{AnimeData(it)}
    }
}

data class AnimeAttributes(
        val canonicalTitle: String,
        val titles: Titles,
        val synopsis: String,
        val rating: String,
        val startDate: String,
        val endDate: String,
        val popularityRank: Int,
        val ratingRank: Int,
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
            parcel.readString(),
            parcel.readParcelable(Titles::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(PosterImage::class.java.classLoader),
            parcel.readParcelable(CoverImage::class.java.classLoader),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(canonicalTitle)
        parcel.writeParcelable(titles, flags)
        parcel.writeString(synopsis)
        parcel.writeString(rating)
        parcel.writeString(startDate)
        parcel.writeString(endDate)
        parcel.writeInt(popularityRank)
        parcel.writeInt(ratingRank)
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

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{AnimeAttributes(it)}
    }
}

class Titles(
        val en: String,
        val en_jp: String,
        val ja_jp: String
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(en)
        parcel.writeString(en_jp)
        parcel.writeString(ja_jp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{Titles(it)}
    }
}

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

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{PosterImage(it)}
    }
}

class CoverImage(
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

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel{CoverImage(it)}
    }
}