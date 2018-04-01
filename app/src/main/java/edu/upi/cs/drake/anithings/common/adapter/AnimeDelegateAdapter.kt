package edu.upi.cs.drake.anithings.common.adapter

import android.support.v4.view.ViewCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.extensions.inflate
import edu.upi.cs.drake.anithings.common.extensions.loadImg
import edu.upi.cs.drake.anithings.data.local.AnimeData
import kotlinx.android.synthetic.main.anime_item_grid.view.*

/**
 * Created by drake on 3/28/2018.
 *
 */
class AnimeDelegateAdapter(listener: RecyclerViewOnClickListener): ViewTypeDelegateAdapter {

    private var mListener = listener

    override fun onCreateViewHolder(parent: ViewGroup): AnimeViewHolder{
        val view = parent.inflate(R.layout.anime_item_grid)
        val cardView = view.findViewById(R.id.card_view) as CardView
        cardView.maxCardElevation = 2.0F
        cardView.radius = 5.0F
        return AnimeViewHolder(view, mListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AnimeViewHolder
        holder.bind(item as AnimeData)
    }

    class AnimeViewHolder(view: View, listener: RecyclerViewOnClickListener): RecyclerView.ViewHolder(view), View.OnClickListener {

        private var mListener = listener
        private val imgThumbnail = view.thumbnail
        private val title = view.title

        init {
            mListener = listener
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            mListener.onClick(v, adapterPosition)
        }

        fun bind(item: AnimeData) {
            imgThumbnail.loadImg(item.posterImage)
            title.text = item.canonicalTitle
            ViewCompat.setTransitionName(imgThumbnail, item.canonicalTitle)
        }
    }
}
