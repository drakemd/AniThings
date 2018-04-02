package edu.upi.cs.drake.anithings.data.local

sealed class Result<T> {
    abstract val message: String?
    abstract val data: T

    data class Success<T>(override val message: String?, override val data: T): Result<T>()
    data class Loading<T>(override val message: String?, override val data: T): Result<T>()
    data class Error<T>(override val message: String?, override val data: T): Result<T>()
}