package edu.upi.cs.drake.anithings.data.local.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.ViewType

@Entity(tableName = "anime")
data class AnimeEntity(
        @PrimaryKey
        var id: Int = 0,
        var canonicalTitle: String = "n/a",
        var synopsis: String = "n/a",
        var averageRating: String? = "n/a",
        var startDate: String? = "n/a",
        var endDate: String? = "n/a",
        var popularityRank: Int = 0,
        var ratingRank: Int? = null,
        var ageRating: String? = null,
        var ageRatingGuide: String? = null,
        var status: String = "n/a",
        var genres: List<Int> = emptyList(),
        var posterImage: String = "n/a",
        var coverImage: String? = "n/a",
        var youtubeVideoId: String? = "n/a",
        var showType: String = "n/a",
        var timestamp: Long = System.nanoTime()
): ViewType{
    override fun getViewType(): Int {
        return AdapterConstants.ANIME
    }
}