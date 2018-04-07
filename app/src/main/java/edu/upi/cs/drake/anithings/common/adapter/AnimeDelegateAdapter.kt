package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.databinding.AnimeItemBinding
import edu.upi.cs.drake.anithings.view.animelist.AnimeListActivity
import edu.upi.cs.drake.anithings.view.animelist.AnimeListCallback
import kotlinx.android.synthetic.main.anime_item.view.*

/**
 * Created by drake on 3/28/2018.
 * this class inflate and bind layout for animeItem in the list
 * this class is a delegate adapter used in [AnimeAdapter]
 */
class AnimeDelegateAdapter(animeListCallback: AnimeListCallback): ViewTypeDelegateAdapter {

    private var animeCallback = animeListCallback

    override fun onCreateViewHolder(parent: ViewGroup) =
            AnimeViewHolder.create(LayoutInflater.from(parent.context), parent, animeCallback)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AnimeViewHolder
        holder.bind(item as AnimeEntity)
    }

    class AnimeViewHolder(var binding: AnimeItemBinding, var animeListCallback: AnimeListCallback): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener({animeListCallback.onAnimeClick(binding.anime, binding.thumbnail)})
        }

        fun bind(item: AnimeEntity) {
            binding.anime = item
            binding.executePendingBindings()
        }

        //a static method to inflate and bind the layout and create a new instance of this class
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
