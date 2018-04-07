package edu.upi.cs.drake.anithings.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import edu.upi.cs.drake.anithings.common.IO_EXECUTOR
import edu.upi.cs.drake.anithings.data.AnimeRepository
import edu.upi.cs.drake.anithings.data.Resource
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by drake on 3/27/2018.
 *
 */
class AnimeListViewModel @Inject constructor(private val animeRepository: AnimeRepository): ViewModel(){

    //variables that will be used as request parameters to the API through repository
    private var pageNum = 0
    private var mode = 1
    private val limit = 15
    private val maxPage = 10
    private var sortBy: String? = null
    private var status: String? = null
    private var searchFilter: String? = null

    private val allDisposable: CompositeDisposable = CompositeDisposable()
    val stateLiveData: MutableLiveData<Resource<List<AnimeEntity>>> = MutableLiveData()

    //this method check genres count and fetch genres from the API if its 0
    fun checkGenre(){
        animeRepository.getCountGenres()
                .subscribeOn(Schedulers.from(IO_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it ->
                    if(it == 0) animeRepository.fetchAndAddAllGenres()
                })
    }

    //request next page data from repository
    fun nextPage(){
        pageNum+=1
        unSubscribeRepo()
        subscribeRepo()
    }

    //delete all anime from room database and then request new data from the API through the repository
    fun deleteAllAnime(){
        animeRepository.deleteAllAnime().subscribe({
            stateLiveData.value = Resource.Loading(null)
            pageNum = 0
            subscribeRepo()
        })
    }

    //request anime data from the repository
    fun subscribeRepo(){
        when(mode){
            1 ->{
                sortBy = "popularityRank"
            }
            2 ->{
                sortBy = "ratingRank"
            }
            3 ->{
                sortBy = "popularityRank"
                status = "current"
            }
        }

        val disposable = animeRepository
                .getAnimeList(sortBy, pageNum, limit, status, searchFilter, maxPage)
                .subscribe(
                        { it -> stateLiveData.value = it },
                        { Log.d("viewmodel", it.message)}
                )
        allDisposable.add(disposable)
    }

    fun unSubscribeRepo(){
        allDisposable.clear()
    }

    fun setPage(listSize: Int){
        pageNum = (listSize/limit) - 1
    }

    fun setMode(newMode: Int){
        mode = newMode
    }

    fun setSearchFilter(newSearchFilter: String?){
        searchFilter = newSearchFilter
    }

    fun getMode() = mode
}