package edu.upi.cs.drake.anithings.data.api

import edu.upi.cs.drake.anithings.data.api.model.KitsuAnimeResponse
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
                @Query("page[offset]") offset: Int,
                @Query("filter[status]") status: String,
                @Query("include") genre: String = "genres"):
            Single<KitsuAnimeResponse>
}