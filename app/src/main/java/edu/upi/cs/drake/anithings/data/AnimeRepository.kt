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
 * this class implements [Repository] and should be the only class accessible to the outside of data layer
 * this class is a bridge between view model layer and data layer
 */
class AnimeRepository @Inject constructor(private val remoteAnimeDataSource: RemoteAnimeDataSource,
                                          private val localAnimeDatasource: LocalAnimeDatasource): Repository {

    override fun getAnimeList(sortBy: String?, page: Int, limit: Int, status: String?, search: String?, maxPage: Int): Flowable<Resource<List<AnimeEntity>>> {
        return object: NetworkBoundResource<List<AnimeEntity>, List<AnimeEntity>>(){

            override fun saveCallResult(item: List<AnimeEntity>) {
                saveAnimeJoinGenres(item)
            }

            override fun shouldFetch(data: List<AnimeEntity>?): Boolean {

                return (data == null || data.isEmpty()|| data.size < ((page + 1) * limit) && (page + 1) <= maxPage)
            }

            override fun loadFromDb(): Single<List<AnimeEntity>> {
                return localAnimeDatasource.animeDao().getCurrentAnime()
            }

            override fun createCall(): Single<List<AnimeEntity>> {
                return remoteAnimeDataSource
                        .getAnimeListByPage(sortBy, page, limit, status, search)
            }
        }.asFlowable()
    }

    //fetch all genres from the api an insert it to room database
    override fun fetchAndAddAllGenres(){
        remoteAnimeDataSource.getAllGenres()
                .subscribeOn(Schedulers.from(NETWORK_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({it-> saveAllGenres(it)},{Log.d("repository", "add genre error")})
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

    override fun deleteAllAnime(): Completable{
        return Completable.fromAction {localAnimeDatasource.animeDao().deleteAllAnime()}
                .subscribeOn(Schedulers.from(IO_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
    }

    //insert all anime and its genres from the api to Room database
    private fun saveAnimeJoinGenres(animeEntities: List<AnimeEntity>){
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

    //insert all genres obtained from API to room database
    private fun saveAllGenres(it: List<GenresEntity>){
        Completable.fromAction {localAnimeDatasource.genresDao().inserGenres(it)}
                .subscribeOn(Schedulers.from(IO_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({Log.d("repo","success add all genre")}, {error->Log.d("repo",error.message)})
    }
}