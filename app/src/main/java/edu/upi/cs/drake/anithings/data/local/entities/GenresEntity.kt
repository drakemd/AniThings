package edu.upi.cs.drake.anithings.data.local.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "genres")
data class GenresEntity(
        @PrimaryKey
        var id: Int = 0,
        var name: String = ""
)