package edu.upi.cs.drake.anithings.repository.model

import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.ViewType

/**
 * Created by drake on 3/27/2018.
 *
 */

class AnimeData(
        val id: Int,
        val type: String,
        val attributes: AnimeAttributes
): ViewType { override fun getViewType() = AdapterConstants.ANIME }

class AnimeAttributes(
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
)

class Titles(
        val en: String,
        val en_jp: String,
        val ja_jp: String
)

class PosterImage(
        val tiny: String,
        val small: String,
        val medium: String,
        val large: String,
        val original: String
)

class CoverImage(
        val tiny: String,
        val small: String,
        val large: String,
        val original: String
)