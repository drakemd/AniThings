package edu.upi.cs.drake.anithings.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import edu.upi.cs.drake.anithings.common.IO_EXECUTOR
import edu.upi.cs.drake.anithings.data.AnimeRepository
import edu.upi.cs.drake.anithings.data.Resource
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by drake on 3/27/2018.
 *
 */
class AnimeListViewModel @Inject constructor(private val animeRepository: AnimeRepository): ViewModel(){

    private var pageNum = 0
    private var limit = 15

    private val allDisposable: MutableList<Disposable> = arrayListOf()
    val stateLiveData: MutableLiveData<Resource<List<AnimeEntity>>> = MutableLiveData()

    init {
        subscribeRepo()
    }

    fun nextPage(){
        pageNum+=1
        subscribeRepo()
    }

    fun onDestroy(){
        allDisposable.forEach {
            it.dispose()
        }
    }

    fun setPage(listSize: Int){
        pageNum = (listSize/limit) - 1
    }

    fun subscribeRepo(){
        val disposable = animeRepository.getPopularAnime(pageNum, limit)
                .subscribe(
                        { it -> stateLiveData.value = it },
                        { Log.d("viewmodel", it.message)}
                )
        allDisposable.add(disposable)
    }

    fun checkGenre(){
        animeRepository.getCountGenres()
                .subscribeOn(Schedulers.from(IO_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it ->
                        Log.d("viewmodel", it.toString())
                        if(it == 0){
                            animeRepository.fetchAndAddAllGenres()
                        }
                })
    }
}