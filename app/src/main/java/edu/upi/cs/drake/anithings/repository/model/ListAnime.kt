package edu.upi.cs.drake.anithings.repository.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by drake on 3/27/2018.
 *
 */

data class ListAnime(val data: List<NewAnimeData>): Parcelable{

    private constructor(parcelIn: Parcel): this(
            mutableListOf<NewAnimeData>().apply {
                parcelIn.readTypedList(this, NewAnimeData.CREATOR)
            }
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeTypedList(data)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ListAnime> {
        override fun createFromParcel(parcel: Parcel): ListAnime {
            return ListAnime(parcel)
        }

        override fun newArray(size: Int): Array<ListAnime?> {
            return arrayOfNulls(size)
        }
    }
}