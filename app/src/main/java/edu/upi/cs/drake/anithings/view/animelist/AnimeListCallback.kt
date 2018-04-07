package edu.upi.cs.drake.anithings.view.animelist

import android.view.View
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity

/**
 * callback to be implemented in [AnimeListFragment]
 */
interface AnimeListCallback {
    fun onAnimeClick(anime: AnimeEntity?, sharedView: View)
    fun onConnectionError()
}