package edu.upi.cs.drake.anithings.view.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import edu.upi.cs.drake.anithings.R
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.transition.TransitionInflater
import dagger.android.AndroidInjection
import edu.upi.cs.drake.anithings.viewmodel.AnimeDetailViewModel
import edu.upi.cs.drake.anithings.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.content_detail.*
import javax.inject.Inject
import android.databinding.DataBindingUtil
import android.transition.TransitionManager
import android.view.LayoutInflater
import edu.upi.cs.drake.anithings.databinding.ActivityAnimeDetailBinding
import edu.upi.cs.drake.anithings.databinding.GenresItemBinding


class AnimeDetail : AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var animeDetailViewModel: AnimeDetailViewModel
    private lateinit var binding: ActivityAnimeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_anime_detail)
        animeDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(AnimeDetailViewModel::class.java)
        setView()
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }


    private fun setView(){
        supportPostponeEnterTransition()
        initCollapsingToolbar()
        window.sharedElementEnterTransition =
                TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition)
        window.enterTransition =
                TransitionInflater.from(this).inflateTransition(R.transition.appbar_transition).addTarget(binding.layout1?.appbar)

        populateView()
    }

    private fun populateView(){
        val id = intent?.extras?.getInt(KEY_ANIME_ID)
        id?.let {
            animeDetailViewModel.run {
                setId(it)
                update()
                anime.observe(this@AnimeDetail, Observer {
                    binding.anime = it
                    supportStartPostponedEnterTransition()
                })
                getAnimeGenres()
                genres.observe(this@AnimeDetail, Observer {
                    it?.let { it1 -> inflateGenres(it1) }
                })
            }
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private fun initCollapsingToolbar(){
        setSupportActionBar(binding.layout1?.toolbar)
        val collapsingToolbar = binding.layout1?.collapsingToolbar as CollapsingToolbarLayout
        collapsingToolbar.title = " "
        val appBarLayout = binding.layout1?.appbar
        appBarLayout?.setExpanded(true)
        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout?.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.title = binding.anime?.canonicalTitle
                    isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    isShow = false
                }
            }
        })
    }

    @SuppressLint("InflateParams")
    private fun inflateGenres(genres: List<String>){
        val container = genres_container
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        genres.forEach {
            val binding: GenresItemBinding = DataBindingUtil.inflate(inflater, R.layout.genres_item,null, false)
            binding.genre = it
            container.addView(binding.root)
        }
    }

    companion object {
        const val KEY_ANIME_ID = "key_anime_id"
        fun newIntent(context: Context, animeId: Int): Intent{
            val intent = Intent(context, AnimeDetail::class.java)
            intent.putExtra(KEY_ANIME_ID, animeId)
            return intent
        }
    }
}
