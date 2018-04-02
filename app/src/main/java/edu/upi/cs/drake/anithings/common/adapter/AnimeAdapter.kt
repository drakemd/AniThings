package edu.upi.cs.drake.anithings.common.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.data.local.entities.AnimeData
import edu.upi.cs.drake.anithings.data.local.entities.AnimeDataDiffCallback

/**
 * Created by drake on 3/28/2018.
 * anime adapter (RecyclerView.Adapter) created from delegates adapter
 */
class AnimeAdapter(listener: RecyclerViewOnClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType> //ArrayListOf<ViewType>
    private var delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()


    private val loadingItem = object: ViewType{
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapter.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapter.put(AdapterConstants.ANIME, AnimeDelegateAdapter(listener))
        items = arrayListOf()
        items.add(loadingItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapter.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapter.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addAnime(anime: List<AnimeData>){
        //first remove loading and notify
        val initPosition = items.size - 1

        val diffCallback = AnimeDataDiffCallback(this.items, anime)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        Log.d("init", initPosition.toString())
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        //insert anime and loading at the end of the list
        items.clear()
        items.addAll(anime)
        diffResult.dispatchUpdatesTo(this)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size)
    }

    fun clearAndAddAnime(anime: List<AnimeData>){
        //first clear all and notify
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        //insert anime
        items.addAll(anime)
        //items.add(loadingItem)
        notifyItemRangeChanged(0, items.size)
    }

    fun getAnime(): List<AnimeData> = items
            .filter { it.getViewType() == AdapterConstants.ANIME }
            .map { it as AnimeData }

    private fun getLastPosition() = if(items.lastIndex == -1) 0 else items.lastIndex
}