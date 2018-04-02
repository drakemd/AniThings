package edu.upi.cs.drake.anithings.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

import edu.upi.cs.drake.anithings.common.domain.IPopularAnimeUseCase
import edu.upi.cs.drake.anithings.data.AnimeRepository


/**
 * Created by drake on 3/27/2018.
 *
 */

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private val animeRepository: AnimeRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(AnimeViewModel::class.java)) {
            return AnimeViewModel(animeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}