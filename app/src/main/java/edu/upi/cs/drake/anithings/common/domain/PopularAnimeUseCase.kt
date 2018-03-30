package edu.upi.cs.drake.anithings.common.domain

import edu.upi.cs.drake.anithings.repository.IAnimeDbService
import edu.upi.cs.drake.anithings.repository.model.AnimeData
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by drake on 3/27/2018.
 * use case implementation for popular anime
 */

class PopularAnimeUseCase @Inject constructor(private val IAnimeDbService: IAnimeDbService) : IPopularAnimeUseCase {

    /*
    * get popular anime from animeDbService and map it to List<AnimeData>
    */

    private val limit = 15

    override fun getPopularAnimeByPage(page: Int): Single<List<AnimeData>> {
        return IAnimeDbService.getPopularAnime("popularityRank", limit, page*limit)
                .map { it -> it.data }
    }
}