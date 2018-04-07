package edu.upi.cs.drake.anithings.data.remote

import edu.upi.cs.drake.anithings.data.remote.model.KitsuAnimeResponse
import edu.upi.cs.drake.anithings.data.remote.model.KitsuGenresResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by drake on 3/27/2018.
 * a generic interface to handle request and specified its parameters to the API
 */
interface RemoteAnimeService {

    @GET("anime")
    fun getAnimeList(
            @Query("sort") sortBy: String?,
            @Query("page[limit]") limit: Int,
            @Query("page[offset]") offset: Int,
            @Query("filter[status]") status: String?,
            @Query("filter[text]") search: String?,
            @Query("include") genre: String = "genres"):
            Single<KitsuAnimeResponse>

    @GET("genres")
    fun getAllGenres(@Query("page[limit]") limit: Int):
            Single<KitsuGenresResponse>
}