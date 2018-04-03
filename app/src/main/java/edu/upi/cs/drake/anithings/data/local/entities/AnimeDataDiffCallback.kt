package edu.upi.cs.drake.anithings.data.local.entities

import android.support.v7.util.DiffUtil
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.ViewType

class AnimeDataDiffCallback(val oldAnimeList: List<ViewType>, val newAnimeList: List<ViewType>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if(oldAnimeList[oldItemPosition].getViewType() == newAnimeList[newItemPosition].getViewType()){
            if(oldAnimeList[oldItemPosition].getViewType() == AdapterConstants.ANIME){
                if((oldAnimeList[oldItemPosition] as AnimeEntity).id == (newAnimeList[newItemPosition] as AnimeEntity).id){
                    return true
                }
            }
        }
        return false
    }

    override fun getOldListSize() = oldAnimeList.size

    override fun getNewListSize() = newAnimeList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean{
        val oldItem = oldAnimeList[oldItemPosition]
        val newItem = newAnimeList[newItemPosition]

        if(oldItem.getViewType() == newItem.getViewType()){
            if(oldItem.getViewType() == AdapterConstants.ANIME){
                if((oldItem as AnimeEntity).canonicalTitle.equals((newItem as AnimeEntity).canonicalTitle)){
                    return true
                }
            }
        }
        return false
    }
}