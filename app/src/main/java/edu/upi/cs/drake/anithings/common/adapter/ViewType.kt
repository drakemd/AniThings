package edu.upi.cs.drake.anithings.common.adapter

/**
 * Created by drake on 3/28/2018.
 * this interface implemented by items used in [AnimeAdapter]
 */
interface ViewType {
    /**
    * get a viewtype of a delegate adapter
    * @return a view type specified in [AdapterConstants]
    */
    fun getViewType(): Int
}