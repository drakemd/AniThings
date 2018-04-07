package edu.upi.cs.drake.anithings.data.local.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE

@Entity(tableName = "anime_genres_join",
        primaryKeys = ["animeId", "genresId"],
        foreignKeys = [ForeignKey(entity = AnimeEntity::class, parentColumns = ["id"], childColumns = ["animeId"], onDelete = CASCADE),
                        ForeignKey(entity = GenresEntity::class, parentColumns = ["id"], childColumns = ["genresId"])])
data class AnimeJoinGenres(
        var animeId: Int = 0,
        var genresId: Int = 0
)