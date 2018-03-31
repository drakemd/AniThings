package edu.upi.cs.drake.anithings.common.domain

import edu.upi.cs.drake.anithings.data.DbRepository
import edu.upi.cs.drake.anithings.data.api.model.AnimeDataResponse
import edu.upi.cs.drake.anithings.data.local.NewAnimeData
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by drake on 3/27/2018.
 * use case implementation for popular anime
 */

class PopularAnimeUseCase @Inject constructor(private val DbRepository: DbRepository) : IPopularAnimeUseCase {

    /*
    * get popular anime from animeDbService and map it to List<AnimeData>
    */

    private val limit = 15

    override fun getPopularAnimeByPage(page: Int): Single<List<NewAnimeData>> {
        return DbRepository.getPopularAnime("popularityRank", limit, page*limit)
                .map { it.data.map { mapRawToEntity(it) } }
    }

    fun mapRawToEntity(it: AnimeDataResponse): NewAnimeData{
        return NewAnimeData(
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
}