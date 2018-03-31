package edu.upi.cs.drake.anithings.common.model

import edu.upi.cs.drake.anithings.repository.model.AnimeData
import edu.upi.cs.drake.anithings.repository.model.NewAnimeData

/**
 * Created by drake on 3/27/2018.
 * model class to observe anime data state
 */
sealed class AnimeListState {
    abstract val pageNum: Int
    abstract val loadedAllItems: Boolean
    abstract val allData: List<NewAnimeData>
    abstract val newData: List<NewAnimeData>

    data class DefaultState(override val pageNum: Int, override val loadedAllItems: Boolean, override val allData: List<NewAnimeData>, override val newData: List<NewAnimeData>) : AnimeListState()
    data class LoadingState(override val pageNum: Int, override val loadedAllItems: Boolean, override val allData: List<NewAnimeData>, override val newData: List<NewAnimeData>) : AnimeListState()
    data class ErrorState(val errorMessage: String, override val pageNum: Int, override val loadedAllItems: Boolean, override val allData: List<NewAnimeData>, override val newData: List<NewAnimeData>) : AnimeListState()
}