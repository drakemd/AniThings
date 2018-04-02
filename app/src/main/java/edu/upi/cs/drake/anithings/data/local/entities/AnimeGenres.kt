package edu.upi.cs.drake.anithings.data.local.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "animegenres", primaryKeys = ["animeId","genreId"])
data class AnimeGenres(
        var animeId: Int = 0,
        var genreId: Int = 0
)