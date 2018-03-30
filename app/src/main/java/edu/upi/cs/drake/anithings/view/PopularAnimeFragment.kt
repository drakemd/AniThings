package edu.upi.cs.drake.anithings.view


import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import dagger.android.support.AndroidSupportInjection
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.InfiniteScrollListener
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.AnimeAdapter
import edu.upi.cs.drake.anithings.common.adapter.RecyclerViewOnClickListener
import edu.upi.cs.drake.anithings.common.model.AnimeListState
import edu.upi.cs.drake.anithings.detail.AnimeDetail
import edu.upi.cs.drake.anithings.repository.model.AnimeData
import edu.upi.cs.drake.anithings.repository.model.ListAnime
import edu.upi.cs.drake.anithings.viewmodel.ViewModelFactory
import edu.upi.cs.drake.anithings.viewmodel.PopularAnimeViewModel
import kotlinx.android.synthetic.main.fragment_popular_anime.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class PopularAnimeFragment : Fragment(), RecyclerViewOnClickListener {

    val TAG = "ListAnimeFragment"

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var popularAnimeViewModel: PopularAnimeViewModel
    private lateinit var animeRecyclerView: RecyclerView

    private var isLoading = false
    private var isLastPage = false

    private var savedInstanceState: Bundle? = null

    private val stateObserver = Observer<AnimeListState> { state ->
        state?.let {
            isLastPage = state.loadedAllItems
            when (state) {
                is AnimeListState.DefaultState -> {
                    isLoading = false
                    addAnime(state.newData)
                }
                is AnimeListState.LoadingState -> {
                    isLoading = true
                }
                is AnimeListState.ErrorState -> {
                    isLoading = false
                    Log.d("error", state.errorMessage)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        super.onActivityCreated(savedInstanceState)

        var layoutGrid = GridLayoutManager(context, 3)
        layoutGrid = setLayoutManager(layoutGrid)
        animeRecyclerView = anime_list

        animeRecyclerView.apply {
            layoutManager = layoutGrid
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener( {popularAnimeViewModel.updateAnimeList()} , layoutGrid))
        }
        initAdapter()
    }

    override fun onClick(view: View, position: Int) {
        startDetailActivity((animeRecyclerView.adapter as AnimeAdapter).getAnime()[position], view)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val anime = (animeRecyclerView.adapter as AnimeAdapter).getAnime()
        val listAnime = ListAnime(anime)
        if(anime.isNotEmpty()){
            outState.putParcelable(KEY_ANIME, listAnime.copy(data = anime))
        }
    }

    private fun initAdapter() {
        if(animeRecyclerView.adapter == null){
            animeRecyclerView.adapter = AnimeAdapter(this)
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularAnimeViewModel = ViewModelProviders.of(this, viewModelFactory).get(PopularAnimeViewModel::class.java)
        observeViewModel()
        savedInstanceState?.let {
            popularAnimeViewModel.restoreAnimeList()
        } ?: popularAnimeViewModel.updateAnimeList()
    }

    override fun onDestroy() {
        super.onDestroy()
        popularAnimeViewModel.stateLiveData.removeObserver(stateObserver)
    }

    private fun observeViewModel() {
        popularAnimeViewModel.stateLiveData.observe(this, stateObserver)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_popular_anime, container, false)
    }

    private fun addAnime(anime: List<AnimeData>){
        if(savedInstanceState!=null&& savedInstanceState!!.containsKey(KEY_ANIME)){
            val listAnime = savedInstanceState!!.get(KEY_ANIME) as ListAnime
            (animeRecyclerView.adapter as AnimeAdapter).clearAndAddAnime(listAnime.data)
            savedInstanceState = null
        }else{
            (animeRecyclerView.adapter as AnimeAdapter).addAnime(anime)
        }
    }

    private fun setLayoutManager(layoutGrid: GridLayoutManager): GridLayoutManager{

        val orientation = resources.configuration.orientation
        layoutGrid.spanCount = if(orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3

        layoutGrid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (animeRecyclerView.adapter.getItemViewType(position)) {
                    AdapterConstants.LOADING -> layoutGrid.spanCount
                    else -> 1
                }
            }
        }

        return layoutGrid
    }

    private fun startDetailActivity(anime: AnimeData, view: View){

        Log.d("animedebug", anime.attributes.canonicalTitle)

        val container = activity
        val bundle = Bundle()
        bundle.putParcelable("ANIME", anime)
        val intent = Intent(container, AnimeDetail::class.java)
        intent.putExtra("BUNDLE", bundle)

        intent.putExtra("SHARED_ELEMENT_NAME", anime.attributes.canonicalTitle)

        val thumbnail = view.findViewById<ImageView>(R.id.thumbnail)

        val pair = android.support.v4.util.Pair.create(thumbnail as View, anime.attributes.canonicalTitle)
        Log.d("shared1", anime.attributes.canonicalTitle)
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(container as Activity, pair)
        startActivity(intent, options.toBundle())
    }

    companion object {
        private const val KEY_ANIME = "anime"
    }
}// Required empty public constructor
