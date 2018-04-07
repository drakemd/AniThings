package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.databinding.RetryConnectionItemBinding
import edu.upi.cs.drake.anithings.view.animelist.AnimeListActivity
import edu.upi.cs.drake.anithings.view.animelist.AnimeListCallback

/**
 * Created by drake on 3/28/2018.
 * this class inflate and bind layout for error item in the list
 * this class is a delegate adapter used in [AnimeAdapter]
 * this class more or less works like the [AnimeDelegateAdapter]
 */
class RequestErrorDelegateAdapter(animeListCallback: AnimeListCallback): ViewTypeDelegateAdapter {

    private var connectionCallback = animeListCallback

    override fun onCreateViewHolder(parent: ViewGroup) =
            RequestErrorViewHolder.create(LayoutInflater.from(parent.context), parent, connectionCallback)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class RequestErrorViewHolder(var binding: RetryConnectionItemBinding, var callback: AnimeListCallback):
            RecyclerView.ViewHolder(binding.root){

        init {
            binding.buttonRetry.setOnClickListener({callback.onConnectionError()})
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup, listener: AnimeListCallback): RequestErrorViewHolder{
                val retryConnectionItemBinding: RetryConnectionItemBinding = RetryConnectionItemBinding.inflate(inflater, parent, false)

                return RequestErrorViewHolder(retryConnectionItemBinding, listener)
            }
        }
    }
}