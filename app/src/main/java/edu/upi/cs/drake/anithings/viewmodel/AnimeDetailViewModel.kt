package edu.upi.cs.drake.anithings.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import edu.upi.cs.drake.anithings.common.IO_EXECUTOR
import edu.upi.cs.drake.anithings.data.AnimeRepository
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AnimeDetailViewModel @Inject constructor(private val animeRepository: AnimeRepository): ViewModel(){

    private val allDisposable: MutableList<Disposable> = arrayListOf()

    private var id: Int = 0
    val anime: MutableLiveData<AnimeEntity> = MutableLiveData()
    val genres: MutableLiveData<List<String>> = MutableLiveData()

    fun setId(id: Int){
        this.id = id
    }

    fun update(){
        subscribeAnime()
    }

    fun onDestroy(){
        allDisposable.forEach {
            it.dispose()
        }
    }

    fun subscribeAnime(){
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
}