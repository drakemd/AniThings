package edu.upi.cs.drake.anithings.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import edu.upi.cs.drake.anithings.data.local.dao.AnimeDao
import edu.upi.cs.drake.anithings.data.local.entities.AnimeData
import edu.upi.cs.drake.anithings.data.local.entities.AnimeGenres

@Database(entities = [AnimeData::class, AnimeGenres::class], version = 3)
abstract class LocalAnimeDatasource: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}