package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.common.extensions.inflate

/**
 * Created by drake on 3/28/2018.
 * this class inflate and bind layout for generic item which only needs to inflate the [RecyclerView.ViewHolder]
 * this class is a delegate adapter used in [AnimeAdapter]
 */
class GenericDelegateAdapter(private val layoutId: Int): ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return GenericViewHolder(parent, layoutId)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class GenericViewHolder(parent: ViewGroup, layoutId: Int): RecyclerView.ViewHolder(parent.inflate(layoutId = layoutId))
}