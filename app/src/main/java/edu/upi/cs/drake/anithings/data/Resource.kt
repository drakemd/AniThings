package edu.upi.cs.drake.anithings.data

/**
 * a generic sealed class to store data and its state
 */
sealed class Resource<T>{
    abstract val data: T?

    data class Success<T>(override val data: T): Resource<T>()
    data class Loading<T>(override val data: T?): Resource<T>()
    data class Error<T>(val errorMessage: String, override val data: T?): Resource<T>()
}