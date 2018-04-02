package edu.upi.cs.drake.anithings.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import edu.upi.cs.drake.anithings.data.local.entities.AnimeData
import edu.upi.cs.drake.anithings.data.local.entities.AnimeGenres
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface AnimeDao {

    @Query("SELECT * FROM anime ORDER BY timestamp")
    fun getCurrentAnime(): Flowable<List<AnimeData>>

    @Query("SELECT * FROM animegenres WHERE animeId = :entityId")
    fun getAnimeGenres(entityId: Int): Single<List<AnimeGenres>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAnime(animeList: List<AnimeData>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun inserAnimeGenres(animeGenres: List<AnimeGenres>)
}