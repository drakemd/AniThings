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
import dagger.android.AndroidInjection
import edu.upi.cs.drake.anithings.viewmodel.AnimeDetailViewModel
import edu.upi.cs.drake.anithings.viewmodel.ViewModelFactory
import javax.inject.Inject
import android.databinding.DataBindingUtil
import android.transition.TransitionInflater
import android.transition.TransitionSet
import android.view.LayoutInflater
import edu.upi.cs.drake.anithings.databinding.ActivityAnimeDetailBinding
import edu.upi.cs.drake.anithings.databinding.GenresItemBinding

/**
 * this activity provide detail view for anime data
 */
class AnimeDetail : AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var animeDetailViewModel: AnimeDetailViewModel
    private lateinit var binding: ActivityAnimeDetailBinding

    //inflate layout, initialize view model and set/populate the layout
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_anime_detail)
        animeDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(AnimeDetailViewModel::class.java)
        setView()
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

    private fun setView(){
        setTransition()
        initCollapsingToolbar()
        populateView()
    }

    private fun setTransition(){
        postponeEnterTransition()
        val transitionAppBar =
                TransitionInflater.from(this).inflateTransition(R.transition.appbar_transition).addTarget(binding.layout1?.appbar)
        val transitionContent =
                TransitionInflater.from(this).inflateTransition(R.transition.content_transition).addTarget(binding.layout2?.activityBaseContent)

        val set = TransitionSet()
        set.addTransition(transitionAppBar)
        set.addTransition(transitionContent)

        window.enterTransition = set
        window.returnTransition = set
    }

    //Initializing collapsing toolbar will show and hide the toolbar title on scroll
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

    //bind data from the view model to the layout
    private fun populateView(){
        val id = intent?.extras?.getInt(KEY_ANIME_ID)
        id?.let {
            animeDetailViewModel.run {
                update(it)
                anime.observe(this@AnimeDetail, Observer {
                    binding.anime = it
                    startPostponedEnterTransition()
                })
                genres.observe(this@AnimeDetail, Observer {
                    it?.let { it1 -> inflateGenres(it1) }
                })
            }
        }
    }

    //bind and inflate anime genres to the layout
    @SuppressLint("InflateParams")
    private fun inflateGenres(genres: List<String>){
        val container = binding.layout2?.genresContainer
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        genres.forEach {
            val binding: GenresItemBinding = DataBindingUtil.inflate(inflater, R.layout.genres_item,null, false)
            binding.genre = it
            container?.addView(binding.root)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        animeDetailViewModel.unsubscribe()
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
