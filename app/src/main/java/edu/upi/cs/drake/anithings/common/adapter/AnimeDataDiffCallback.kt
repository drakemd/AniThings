package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.util.DiffUtil
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity

/**
 * this class implement [DiffUtil.Callback] to compare new items with old items in the list before adding new items
 * if the items are different then it will be added to the list
 */
class AnimeDataDiffCallback(val oldAnimeList: List<ViewType>, val newAnimeList: List<ViewType>): DiffUtil.Callback() {

    //check if the items are same by comparing individual items in the list
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if(oldAnimeList[oldItemPosition].getViewType() == newAnimeList[newItemPosition].getViewType()){
            if(oldAnimeList[oldItemPosition].getViewType() == AdapterConstants.ANIME){
                if((oldAnimeList[oldItemPosition] as AnimeEntity).id == (newAnimeList[newItemPosition] as AnimeEntity).id){
                    return true
                }
            }else{
                return true
            }
        }
        return false
    }

    override fun getOldListSize() = oldAnimeList.size

    override fun getNewListSize() = newAnimeList.size

    //check if the items are same by comparing individual items in the list
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean{
        val oldItem = oldAnimeList[oldItemPosition]
        val newItem = newAnimeList[newItemPosition]

        if(oldItem.getViewType() == newItem.getViewType()){
            if(oldItem.getViewType() == AdapterConstants.ANIME){
                if((oldItem as AnimeEntity).canonicalTitle.equals((newItem as AnimeEntity).canonicalTitle)){
                    return true
                }
            }else{
                return true
            }
        }
        return false
    }
}