package edu.upi.cs.drake.anithings.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import edu.upi.cs.drake.anithings.data.local.entities.GenresEntity
import io.reactivex.Single

@Dao
interface GenresDao {
    @Query("SELECT name FROM genres WHERE id = :id")
    fun getGenreById(id:Int): Single<List<String>>

    @Query("SELECT COUNT(*) FROM genres")
    fun getCountAllGenres(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserGenres(genres: List<GenresEntity>)
}