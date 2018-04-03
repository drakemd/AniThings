package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.databinding.AnimeItemBinding
import edu.upi.cs.drake.anithings.view.animelist.AnimeListCallback
import kotlinx.android.synthetic.main.anime_item.view.*

/**
 * Created by drake on 3/28/2018.
 *
 */
class AnimeDelegateAdapter(listener: AnimeListCallback): ViewTypeDelegateAdapter {

    private var mListener = listener

    override fun onCreateViewHolder(parent: ViewGroup): AnimeViewHolder{
        return AnimeViewHolder.create(LayoutInflater.from(parent.context), parent, mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AnimeViewHolder
        holder.bind(item as AnimeEntity)
    }

    class AnimeViewHolder(var binding: AnimeItemBinding, var listener: AnimeListCallback): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener({listener.onAnimeClick(binding.anime, binding.thumbnail)})
        }

        fun bind(item: AnimeEntity) {
            binding.anime = item
            binding.executePendingBindings()
        }

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup, listener: AnimeListCallback): AnimeViewHolder{
                val animeItemBinding: AnimeItemBinding = AnimeItemBinding.inflate(inflater, parent, false)
                animeItemBinding.root.card_view.apply {
                    maxCardElevation = 2.0F
                    radius = 5.0F
                }
                return AnimeViewHolder(animeItemBinding, listener)
            }
        }
    }
}
