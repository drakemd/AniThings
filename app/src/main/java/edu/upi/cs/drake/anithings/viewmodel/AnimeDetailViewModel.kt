package edu.upi.cs.drake.anithings.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import edu.upi.cs.drake.anithings.common.IO_EXECUTOR
import edu.upi.cs.drake.anithings.data.AnimeRepository
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * this is the ViewModel for AnimeDetail Activity
 * this ViewModel purpose is to get anime data and its genres from the repository to be used in Activity
 */
class AnimeDetailViewModel @Inject constructor(private val animeRepository: AnimeRepository): ViewModel(){

    private val allDisposable: CompositeDisposable = CompositeDisposable()

    private var id: Int = 0
    val anime: MutableLiveData<AnimeEntity> = MutableLiveData()
    val genres: MutableLiveData<List<String>> = MutableLiveData()

    fun update(id: Int){
        this.id = id
        subscribeAnime()
        getAnimeGenres()
    }

    private fun subscribeAnime(){
        val disposable = animeRepository.getAnimeById(id)
                .subscribeOn(Schedulers.from(IO_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({animeData-> anime.value = animeData})
        allDisposable.add(disposable)
    }

    fun getAnimeGenres(){
        val disposable = animeRepository.getGenresByAnimeId(id)
                .subscribeOn(Schedulers.from(IO_EXECUTOR))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    genres-> this.genres.value = genres
                })
        allDisposable.add(disposable)
    }

    fun unsubscribe(){
        allDisposable.dispose()
    }
}