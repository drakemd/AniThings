package edu.upi.cs.drake.anithings.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import edu.upi.cs.drake.anithings.data.local.entities.AnimeJoinGenres
import io.reactivex.Single

@Dao
interface AnimeJoinGenresDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAnimeJoinGenresDao(animeJoinGenres: List<AnimeJoinGenres>)

    @Query("SELECT genres.name FROM genres INNER JOIN anime_genres_join " +
            "ON genres.id = anime_genres_join.genresId WHERE anime_genres_join.animeId = :animeId")
    fun getGenresForAnime(animeId: Int): Single<List<String>>

    @Query("DELETE FROM anime_genres_join")
    fun deleteAllAnimeGenres()
}