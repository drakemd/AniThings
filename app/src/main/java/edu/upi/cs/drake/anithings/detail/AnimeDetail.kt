package edu.upi.cs.drake.anithings.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import edu.upi.cs.drake.anithings.R
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.transition.TransitionInflater
import android.widget.TextView
import edu.upi.cs.drake.anithings.common.extensions.loadImg
import edu.upi.cs.drake.anithings.repository.model.AnimeData
import edu.upi.cs.drake.anithings.repository.model.NewAnimeData
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.content_detail.*

class AnimeDetail : AppCompatActivity(){

    var animeTitle: String = "Anithings"
    lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anime_detail)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        title = findViewById(R.id.title)

        setSupportActionBar(toolbar)
        initCollapsingToolbar()

        val b = intent?.getBundleExtra("BUNDLE")
        val anime = b?.getParcelable<NewAnimeData>("ANIME")
        val sharedViewName = intent?.extras?.get("SHARED_ELEMENT_NAME").toString()
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition)
        window.sharedElementEnterTransition = transition

        setView(anime, sharedViewName)
    }

    @SuppressLint("SetTextI18n")
    private fun setView(anime: NewAnimeData?, sharedViewName: String){

        thumbnail.transitionName = sharedViewName

        anime?.let {
            animeTitle = it.canonicalTitle
            it.coverImage?.let { it1 -> backdrop.loadImg(it1) }
            thumbnail.loadImg(it.posterImage)
            title.text = it.canonicalTitle
            overview.text = it.synopsis
            rating.text =  it.averageRating?:"N/A"
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private fun initCollapsingToolbar() {
        val collapsingToolbar = findViewById<View>(R.id.collapsing_toolbar) as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = findViewById<View>(R.id.appbar) as AppBarLayout
        appBarLayout.setExpanded(true)
        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = animeTitle
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }
}
