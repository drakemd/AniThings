package edu.upi.cs.drake.anithings.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import edu.upi.cs.drake.anithings.view.animelist.AnimeListActivity
import android.util.Log

/**
 * Created by drake on 3/28/2018.
 * this class work as a scroll listener that will fire a function if it scrolled
 * to the end of the list, it is implemented in [AnimeListActivity]
 */
class InfiniteScrollListener (
        val func: () -> Unit,
        val layoutManager: LinearLayoutManager): RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if(dy > 0){
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if(loading){
                if(totalItemCount > previousTotal){
                    loading = false
                    previousTotal = totalItemCount
                }
            }

            if(!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)){
                //end has been reached and called the function
                Log.i("InfiniteScrollListener", "end reached")
                func()
                loading = true
            }
        }
    }
}