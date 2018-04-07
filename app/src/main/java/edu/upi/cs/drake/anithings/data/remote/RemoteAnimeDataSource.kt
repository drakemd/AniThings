package edu.upi.cs.drake.anithings.data.remote

import edu.upi.cs.drake.anithings.data.remote.model.AnimeDataResponse
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.data.local.entities.GenresEntity
import io.reactivex.Single
import javax.inject.Inject

/**
 * this class is responsible to get and map response data from the API to a generic Entity
 */
class RemoteAnimeDataSource @Inject constructor(private val remoteAnimeService: RemoteAnimeService) {

    fun getAnimeListByPage(sortBy: String?, page: Int, limit: Int, status: String?, search: String?): Single<List<AnimeEntity>> {
        return remoteAnimeService.getAnimeList(sortBy = sortBy,limit = limit , offset = page * limit, status = status, search = search)
                .map { it.data.map { mapAnimeRawToEntity(it) } }
    }

    fun getAllGenres(): Single<List<GenresEntity>>{
        return remoteAnimeService.getAllGenres(100)
                .map {it.data.map{GenresEntity(it.id, it.attributes.name)}}
    }

    private fun mapAnimeRawToEntity(it: AnimeDataResponse): AnimeEntity = AnimeEntity(
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