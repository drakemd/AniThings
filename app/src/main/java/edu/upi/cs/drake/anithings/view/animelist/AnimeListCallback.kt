package edu.upi.cs.drake.anithings.view.animelist

import android.view.View
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity

interface AnimeListCallback {
    fun onAnimeClick(anime: AnimeEntity?, sharedView: View)
}