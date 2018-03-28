package edu.upi.cs.drake.anithings.repository.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by drake on 3/27/2018.
 */
interface IAnimeApi {
    @GET("anime")
    fun getPopularAnime(
                @Query("sort") sortBy: String,
                @Query("page[limit]") limit: Int,
                @Query("page[offset]") offset: Int):
            Single<KitsuAnimeResponse>
}