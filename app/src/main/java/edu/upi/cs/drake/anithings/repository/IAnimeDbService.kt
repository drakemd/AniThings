package edu.upi.cs.drake.anithings.repository

import edu.upi.cs.drake.anithings.repository.api.KitsuAnimeResponse
import io.reactivex.Single

/**
 * Created by drake on 3/27/2018.
 *
 */
interface IAnimeDbService {
    fun getPopularAnime(sortBy: String, limit: Int, offset:Int): Single<KitsuAnimeResponse>
}