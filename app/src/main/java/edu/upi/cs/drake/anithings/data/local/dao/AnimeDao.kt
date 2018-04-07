package edu.upi.cs.drake.anithings.data.local.dao

import android.arch.persistence.room.*
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import io.reactivex.Single

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY timestamp")
    fun getCurrentAnime(): Single<List<AnimeEntity>>

    @Query("SELECT * FROM anime WHERE id = :id LIMIT 1")
    fun getAnimeById(id:Int): Single<AnimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAnime(animeList: List<AnimeEntity>)

    @Query("DELETE FROM anime")
    fun deleteAllAnime()
}