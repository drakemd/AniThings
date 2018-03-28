package edu.upi.cs.drake.anithings.common.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.extensions.inflate
import edu.upi.cs.drake.anithings.common.extensions.loadImg
import edu.upi.cs.drake.anithings.repository.model.AnimeData
import kotlinx.android.synthetic.main.anime_item.view.*

/**
 * Created by drake on 3/28/2018.
 *
 */
class AnimeDelegateAdapter: ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup) = AnimeViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AnimeViewHolder
        holder.bind(item as AnimeData)
    }

    class AnimeViewHolder(parent: ViewGroup): RecyclerView.ViewHolder(parent.inflate(R.layout.anime_item)) {
        private val imgThumbnail = itemView.img_thumbnail
        private val description = itemView.description
        private val author = itemView.author
        private val comments = itemView.comments
        private val time = itemView.time

        fun bind(item: AnimeData) {
            imgThumbnail.loadImg(item.attributes.posterImage.small)
            description.text = item.attributes.canonicalTitle
            author.text = item.attributes.status
            comments.text = item.attributes.rating
            time.text = item.attributes.startDate
        }
    }
}
