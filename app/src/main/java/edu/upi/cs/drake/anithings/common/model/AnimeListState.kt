package edu.upi.cs.drake.anithings.common.model

/**
 * Created by drake on 3/27/2018.
 * model class to observe anime data state
 */
sealed class AnimeListState<T> {
    abstract val pageNum: Int
    abstract val loadedAllItems: Boolean
    abstract val allData: T
    abstract val newData: T

    data class SuccessState<T>(override val pageNum: Int, override val loadedAllItems: Boolean, override val allData: T, override val newData: T) : AnimeListState<T>()
    data class LoadingState<T>(override val pageNum: Int, override val loadedAllItems: Boolean, override val allData: T, override val newData: T) : AnimeListState<T>()
    data class ErrorState<T>(val errorMessage: String, override val pageNum: Int, override val loadedAllItems: Boolean, override val allData: T, override val newData: T) : AnimeListState<T>()
}