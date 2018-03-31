package edu.upi.cs.drake.anithings.common.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.repository.model.NewAnimeData

/**
 * Created by drake on 3/28/2018.
 * anime adapter (RecyclerView.Adapter) created from delegates adapter
 */
class AnimeAdapter(listener: RecyclerViewOnClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()


    private val loadingItem = object: ViewType{
        override fun getViewType() = AdapterConstants.LOADING
    }

    init {
        delegateAdapter.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapter.put(AdapterConstants.ANIME, AnimeDelegateAdapter(listener))
        items = ArrayList()
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

    fun addAnime(anime: List<NewAnimeData>){
        //first remove loading and notify
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        //insert anime and loading at the end of the list
        items.addAll(anime)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size)
    }

    fun clearAndAddAnime(anime: List<NewAnimeData>){
        //first clear all and notify
        items.clear()
        notifyItemRangeRemoved(0, getLastPosition())

        //insert anime
        items.addAll(anime)
        items.add(loadingItem)
        notifyItemRangeChanged(0, items.size)
    }

    fun getAnime(): List<NewAnimeData> = items
            .filter { it.getViewType() == AdapterConstants.ANIME }
            .map { it as NewAnimeData}

    private fun getLastPosition() = if(items.lastIndex == -1) 0 else items.lastIndex
}