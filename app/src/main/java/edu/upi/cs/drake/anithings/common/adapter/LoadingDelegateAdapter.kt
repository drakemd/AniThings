package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.extensions.inflate

/**
 * Created by drake on 3/28/2018.
 * delegate adapter for loading item
 */
class LoadingDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
    }

    class LoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.loading_item))
}