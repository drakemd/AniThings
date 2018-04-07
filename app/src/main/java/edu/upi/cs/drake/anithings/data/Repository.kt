package edu.upi.cs.drake.anithings.data

import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by drake on 3/27/2018.
 *
 */
interface Repository {
    fun getAnimeList(sortBy: String?, page: Int, limit: Int, status: String?, search: String?, maxPage: Int): Flowable<Resource<List<AnimeEntity>>>
    fun getAnimeById(id: Int): Single<AnimeEntity>
    fun getGenresByAnimeId(id: Int): Single<List<String>>
    fun fetchAndAddAllGenres()
    fun getCountGenres(): Single<Int>
    fun deleteAllAnime(): Completable
}