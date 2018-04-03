package edu.upi.cs.drake.anithings.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.data.local.entities.GenresEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY timestamp")
    fun getCurrentAnime(): Flowable<List<AnimeEntity>>

    @Query("SELECT * FROM anime WHERE id = :id LIMIT 1")
    fun getAnimeById(id:Int): Single<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAnime(animeList: List<AnimeEntity>)
}