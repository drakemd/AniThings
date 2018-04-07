package edu.upi.cs.drake.anithings.common.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.view.animelist.AnimeListCallback

/**
 * Created by drake on 3/28/2018.
 * this class implements RecyclerView.Adapter and use multiple view type as its items
 * those items has its own adapter and use different layout as well
 */
class AnimeAdapter(animeListCallback: AnimeListCallback): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapter = SparseArrayCompat<ViewTypeDelegateAdapter>()

    private val loadingItem = object: ViewType{
        override fun getViewType() = AdapterConstants.LOADING
    }

    private val errorItem = object: ViewType{
        override fun getViewType() = AdapterConstants.ERROR
    }

    private val notFoundItem = object: ViewType{
        override fun getViewType() = AdapterConstants.NOTFOUND
    }

    init {
        delegateAdapter.put(AdapterConstants.LOADING, GenericDelegateAdapter(R.layout.loading_item))
        delegateAdapter.put(AdapterConstants.ANIME, AnimeDelegateAdapter(animeListCallback))
        delegateAdapter.put(AdapterConstants.ERROR, RequestErrorDelegateAdapter(animeListCallback))
        delegateAdapter.put(AdapterConstants.NOTFOUND, GenericDelegateAdapter(R.layout.adapter_item_not_found))
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

    //add anime to the list
    fun addAnime(anime: List<AnimeEntity>){

        val initPosition = items.size - 1
        //diffcallback used so we only added a different anime to the items/no duplicating anime
        val diffCallback = AnimeDataDiffCallback(this.items, anime)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        val isAdded = (diffCallback.oldListSize < diffCallback.newListSize) //are there new anime added?

        //first remove loading and notify if there is
        if(items.size > 0){
            items.removeAt(initPosition)
            notifyItemRemoved(initPosition)
        }

        //insert anime and loading at the end of the list
        items.clear()
        items.addAll(anime)
        diffResult.dispatchUpdatesTo(this)

        //and add loadingItem at the end of the list if there are new anime added
        if (isAdded) {
            items.add(loadingItem)
            notifyItemRangeChanged(initPosition, items.size + 1)
        } else {
            notifyItemRangeChanged(initPosition, items.size)
        }
    }

    //these are the methods to display or remove item from the list
    fun showLoading(){
        items.add(loadingItem)
        notifyItemChanged(items.size)
    }

    fun removeLastItem(){
        items.removeAt(items.size - 1)
        notifyItemRemoved(items.size - 1)
    }

    fun showConnectionError(){
        if(items.size > 0){
            removeLastItem()
        }
        items.add(errorItem)
        notifyItemChanged(items.size - 1)
    }

    fun showDataNotFound(){
        removeLastItem()
        items.add(notFoundItem)
        notifyItemChanged(items.size - 1)
    }
}