package edu.upi.cs.drake.anithings.data.remote

import edu.upi.cs.drake.anithings.data.remote.model.KitsuAnimeResponse
import edu.upi.cs.drake.anithings.data.remote.model.KitsuGenresResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by drake on 3/27/2018.
 */
interface RemoteAnimeService {
    @GET("anime")
    fun getPopularAnime(
                @Query("sort") sortBy: String,
                @Query("page[limit]") limit: Int,
                @Query("page[offset]") offset: Int,
                @Query("filter[status]") status: String,
                @Query("include") genre: String = "genres"):
            Flowable<KitsuAnimeResponse>

    @GET("genres")
    fun getAllGenres(@Query("page[limit]") limit: Int):
            Single<KitsuGenresResponse>
}