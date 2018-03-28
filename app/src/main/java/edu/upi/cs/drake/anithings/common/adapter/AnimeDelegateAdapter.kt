package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.extensions.inflate
import edu.upi.cs.drake.anithings.common.extensions.loadImg
import edu.upi.cs.drake.anithings.repository.model.AnimeData
import kotlinx.android.synthetic.main.anime_item_grid.view.*

/**
 * Created by drake on 3/28/2018.
 *
 */
class AnimeDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): AnimeViewHolder{
        val view = parent.inflate(R.layout.anime_item_grid)
        val cardView = view.findViewById(R.id.card_view) as CardView
        cardView.maxCardElevation = 2.0F
        cardView.radius = 5.0F
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AnimeViewHolder
        holder.bind(item as AnimeData)
    }

    class AnimeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val imgThumbnail = view.thumbnail
        private val title = view.title
        private val rating = view.rating

        fun bind(item: AnimeData) {
            imgThumbnail.loadImg(item.attributes.posterImage.small)
            title.text = item.attributes.canonicalTitle
            rating.text = item.attributes.rating
        }
    }
}
