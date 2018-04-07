package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by drake on 3/28/2018.
 * this is a basic RecyclerView interface implemented by DelegateAdapters used in [AnimeAdapter]
 * more about delegate adapter can be found in http://hannesdorfmann.com/android/adapter-delegates
 */
interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}