package edu.upi.cs.drake.anithings.data.remote.model

import com.squareup.moshi.Json

/**
 * Created by drake on 3/27/2018.
 * these classes purpose is to hold the response data from the API
 */

class KitsuAnimeResponse(@Json(name = "data") val data: List<AnimeDataResponse>,
                         @Json(name = "meta") val meta: Meta)

class Meta(@Json(name = "count") val count: Int)

class AnimeDataResponse(@Json(name = "id") val id: Int,
                        @Json(name = "type") val type: String,
                        @Json(name = "attributes") val attributes: AnimeAttributesResponse,
                        @Json(name = "relationships") val relationships: AnimeRelationshipsResponse
)

class AnimeAttributesResponse(@Json(name = "canonicalTitle") val canonicalTitle: String,
                              @Json(name = "titles") val titles: TitlesResponse,
                              @Json(name = "synopsis") val synopsis: String,
                              @Json(name = "averageRating") val averageRating: String?,
                              @Json(name = "startDate") val startDate: String?,
                              @Json(name = "endDate") val endDate: String?,
                              @Json(name = "popularityRank") val popularityRank: Int,
                              @Json(name = "ratingRank") val ratingRank: Int?,
                              @Json(name = "ageRating") val ageRating: String?,
                              @Json(name = "ageRatingGuide") val ageRatingGuide: String?,
                              @Json(name = "subtype") val subtype: String,
                              @Json(name = "status") val status: String,
                              @Json(name = "tba") val tba: String?,
                              @Json(name = "posterImage") val posterImage: PosterImageResponse,
                              @Json(name = "coverImage") val coverImage: CoverImageResponse?,
                              @Json(name = "episodeCount") val episodeCount: Int?,
                              @Json(name = "episodeLength") val episodeLength: Int?,
                              @Json(name = "youtubeVideoId") val youtubeVideoId: String?,
                              @Json(name = "showType") val showType: String,
                              @Json(name = "nsfw") val nsfw: Boolean
)

class TitlesResponse(@Json(name = "en") val en: String,
                          @Json(name = "en_jp") val en_jp: String,
                          @Json(name = "ja_hp") val ja_jp: String
)

class PosterImageResponse(@Json(name = "tiny") val tiny: String,
                          @Json(name = "small") val small: String,
                          @Json(name = "medium") val medium: String,
                          @Json(name = "large") val large: String,
                          @Json(name = "original") val original: String
)

class CoverImageResponse(@Json(name = "tiny") val tiny: String,
                              @Json(name = "small") val small: String,
                              @Json(name = "large") val large: String,
                              @Json(name = "original") val original: String?
)

class AnimeRelationshipsResponse(@Json(name = "genres") val genres: ListGenresResponse)

class ListGenresResponse(@Json(name = "data") val data: List<GenresResponse>)

class GenresResponse(@Json(name = "id") val id: Int)