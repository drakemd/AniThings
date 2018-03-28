package edu.upi.cs.drake.anithings.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import edu.upi.cs.drake.anithings.repository.api.IAnimeApi
import edu.upi.cs.drake.anithings.common.domain.IPopularAnimeUseCase
import edu.upi.cs.drake.anithings.repository.model.AnimeData
import edu.upi.cs.drake.anithings.common.model.AnimeListState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by drake on 3/27/2018.
 *
 */
class PopularAnimeViewModel @Inject constructor(val popularAnimeUseCase: IPopularAnimeUseCase): ViewModel() {

    @Inject lateinit var api: IAnimeApi

    val stateLiveData =  MutableLiveData<AnimeListState>()

    init {
        stateLiveData.value = AnimeListState.DefaultState(0, false, emptyList(), emptyList())
    }

    fun updateAnimeList() {
        val pageNum = obtainCurrentPageNum()
        AnimeListState.LoadingState(pageNum, false, obtainCurrentData(), emptyList())
        getAnimeList(pageNum)
    }

    fun resetAnimeList() {
        val pageNum = 0
        stateLiveData.value = AnimeListState.LoadingState(pageNum, false, emptyList(), emptyList())
        updateAnimeList()
    }

    fun restoreAnimeList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = AnimeListState.DefaultState(pageNum, false, obtainCurrentData(), emptyList())
    }

    private fun getAnimeList(page:Int){
        popularAnimeUseCase.getPopularAnimeByPage(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onAnimeListReceived, this::onError)
    }

    private fun onAnimeListReceived(animeList: List<AnimeData>) {
        val currentAnimeList = obtainCurrentData().toMutableList()
        val currentPageNum = obtainCurrentPageNum() + 1
        val areAllItemsLoaded = animeList.size > 89
        currentAnimeList.addAll(animeList)
        stateLiveData.value = AnimeListState.DefaultState(currentPageNum, areAllItemsLoaded, currentAnimeList, animeList)
    }

    private fun onError(error: Throwable) {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        stateLiveData.value = AnimeListState.ErrorState(error.message
                ?: "", pageNum, obtainCurrentLoadedAllItems(), obtainCurrentData(), emptyList())
    }

    private fun obtainCurrentPageNum() = stateLiveData.value?.pageNum ?: 0

    private fun obtainCurrentData() = stateLiveData.value?.allData ?: emptyList()

    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false
}