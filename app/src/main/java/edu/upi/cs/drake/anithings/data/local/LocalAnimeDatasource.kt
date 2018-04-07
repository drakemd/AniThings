package edu.upi.cs.drake.anithings.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import edu.upi.cs.drake.anithings.data.local.dao.AnimeDao
import edu.upi.cs.drake.anithings.data.local.dao.AnimeJoinGenresDao
import edu.upi.cs.drake.anithings.data.local.dao.GenresDao
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.data.local.entities.AnimeDataTypeConverter
import edu.upi.cs.drake.anithings.data.local.entities.AnimeJoinGenres
import edu.upi.cs.drake.anithings.data.local.entities.GenresEntity

/**
 * this class is a generic [RoomDatabase] declaring all the dao used by database
 */
@Database(entities = [AnimeEntity::class, GenresEntity::class, AnimeJoinGenres::class], version = 6)
@TypeConverters(AnimeDataTypeConverter::class)
abstract class LocalAnimeDatasource: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun genresDao(): GenresDao
    abstract fun animeGenresJoin(): AnimeJoinGenresDao
}