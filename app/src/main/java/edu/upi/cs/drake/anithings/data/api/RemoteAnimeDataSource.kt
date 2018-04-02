package edu.upi.cs.drake.anithings.data.api

import android.util.Log
import edu.upi.cs.drake.anithings.data.api.model.AnimeDataResponse
import edu.upi.cs.drake.anithings.data.local.entities.AnimeData
import io.reactivex.Single
import javax.inject.Inject

class RemoteAnimeDataSource @Inject constructor(private val remoteAnimeService: RemoteAnimeService) {

    private val limit = 15

    fun getPopularAnimeByPage(page: Int): Single<List<AnimeData>> {
        Log.d("remotedatasource", page.toString())
        return remoteAnimeService.getPopularAnime("-startDate", limit, page * limit, "current")
                .map { it.data.map { mapRawToEntity(it) } }
    }

    private fun mapRawToEntity(it: AnimeDataResponse): AnimeData = AnimeData(
                it.id,
                it.attributes.canonicalTitle,
                it.attributes.synopsis,
                it.attributes.averageRating,
                it.attributes.startDate,
                it.attributes.endDate,
                it.attributes.popularityRank,
                it.attributes.ratingRank,
                it.attributes.ageRating,
                it.attributes.ageRatingGuide,
                it.attributes.status,
                it.relationships.genres.data.map { it.id },
                it.attributes.posterImage.small,
                it.attributes.coverImage?.original ?: "n/a",
                it.attributes.youtubeVideoId,
                it.attributes.showType
        )
}