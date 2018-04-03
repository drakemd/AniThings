package edu.upi.cs.drake.anithings.data

import android.util.Log
import edu.upi.cs.drake.anithings.common.IO_EXECUTOR
import edu.upi.cs.drake.anithings.common.NETWORK_EXECUTOR
import edu.upi.cs.drake.anithings.data.remote.RemoteAnimeDataSource
import edu.upi.cs.drake.anithings.data.local.LocalAnimeDatasource
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.data.local.entities.AnimeJoinGenres
import edu.upi.cs.drake.anithings.data.local.entities.GenresEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by drake on 3/27/2018.
 *
 */
class AnimeRepository @Inject constructor(private val remoteAnimeDataSource: RemoteAnimeDataSource,
                                          private val localAnimeDatasource: LocalAnimeDatasource): Repository {

    override fun getPopularAnime(page:Int, limit: Int): Flowable<Resource<List<AnimeEntity>>> {
        return object: NetworkBoundResource<List<AnimeEntity>, List<AnimeEntity>>(){
            override fun saveCallResult(item: List<AnimeEntity>) {
                saveAnimeJoinGenres(item)
            }

            override fun shouldFetch(data: List<AnimeEntity>?): Boolean {
                Log.d("repository", data?.size.toString() + " < " + ((page + 1) * limit).toString())
                return (data == null || data.isEmpty()|| data.size < ((page + 1) * limit))
            }

            override fun loadFromDb(): Flowable<List<AnimeEntity>> {
                return localAnimeDatasource.animeDao().getCurrentAnime()
            }

            override fun createCall(): Flowable<List<AnimeEntity>> {
                return remoteAnimeDataSource.getPopularAnimeByPage(page, limit)
            }
        }.asFlowable()
    }

    override fun fetchAndAddAllGenres(){
        remoteAnimeDataSource.getAllGenres()
                .subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({it-> saveAllGenres(it)})
    }

    override fun getAnimeById(id: Int): Single<AnimeEntity>{
        return localAnimeDatasource.animeDao().getAnimeById(id)
    }

    override fun getGenresByAnimeId(id: Int): Single<List<String>>{
        return localAnimeDatasource.animeGenresJoin().getGenresForAnime(id)
    }

    override fun getCountGenres(): Single<Int>{
        return localAnimeDatasource.genresDao().getCountAllGenres()
    }

    fun saveAnimeJoinGenres(animeEntities: List<AnimeEntity>){
        val genreList: MutableList<AnimeJoinGenres> = arrayListOf()
        animeEntities.forEach{
            val anime = it
            anime.genres.forEach {
                genreList.add(AnimeJoinGenres(anime.id, it))
            }
        }
        localAnimeDatasource.animeDao().insertAllAnime(animeEntities)
        localAnimeDatasource.animeGenresJoin().insertAnimeJoinGenresDao(genreList)
    }

    fun saveAllGenres(it: List<GenresEntity>){
        Completable.fromAction {localAnimeDatasource.genresDao().inserGenres(it)}
                .subscribeOn(Schedulers.from(IO_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Log.d("repo","success")}, {error->Log.d("repo","error")})
    }
}