package edu.upi.cs.drake.anithings.repository.api

import edu.upi.cs.drake.anithings.repository.IAnimeDbService
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by drake on 3/27/2018.
 *
 */
class AnimeDbService @Inject constructor(val api: IAnimeApi): IAnimeDbService {
    override fun getPopularAnime(sortBy: String, limit: Int, offset: Int): Single<KitsuAnimeResponse> {
        return api.getPopularAnime(sortBy, limit, offset, "current")
    }
}