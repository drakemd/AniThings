package edu.upi.cs.drake.anithings.common.model

import edu.upi.cs.drake.anithings.repository.model.AnimeData

/**
 * Created by drake on 3/27/2018.
 * model class to observe anime data state
 */
sealed class AnimeListState {
    abstract val pageNum: Int
    abstract val loadedAllItems: Boolean
    abstract val data: List<AnimeData>

    data class DefaultState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<AnimeData>) : AnimeListState()
    data class LoadingState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<AnimeData>) : AnimeListState()
    data class ErrorState(val errorMessage: String, override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<AnimeData>) : AnimeListState()
}