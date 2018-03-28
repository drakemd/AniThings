package edu.upi.cs.drake.anithings.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.InfiniteScrollListener
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.AnimeAdapter
import edu.upi.cs.drake.anithings.common.model.AnimeListState
import edu.upi.cs.drake.anithings.repository.model.AnimeData
import edu.upi.cs.drake.anithings.repository.model.ListAnime
import edu.upi.cs.drake.anithings.viewmodel.ViewModelFactory
import edu.upi.cs.drake.anithings.viewmodel.PopularAnimeViewModel
import kotlinx.android.synthetic.main.fragment_popular_anime.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class PopularAnimeFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    lateinit var popularAnimeViewModel: PopularAnimeViewModel

    private var isLoading = false
    private var isLastPage = false

    private var savedInstanceState: Bundle? = null

    private val stateObserver = Observer<AnimeListState> { state ->
        state?.let {
            isLastPage = state.loadedAllItems
            when (state) {
                is AnimeListState.DefaultState -> {
                    isLoading = false
                    if(savedInstanceState!=null&& savedInstanceState!!.containsKey(KEY_ANIME)){
                        val listAnime = savedInstanceState!!.get(KEY_ANIME) as ListAnime
                        (anime_list.adapter as AnimeAdapter).clearAndAddAnime(listAnime.data)
                        savedInstanceState = null
                    }else{
                        addAnime(state.newData)
                    }
                }
                is AnimeListState.LoadingState -> {
                    isLoading = true
                    Log.d("success1", state.pageNum.toString())
                    Log.d("success1", "loadning")
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

        var layoutGrid = GridLayoutManager(context, 2)
        layoutGrid = setLayoutManager(layoutGrid)

        anime_list.apply {
            layoutManager = layoutGrid
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener( {popularAnimeViewModel.updateAnimeList()} , layoutGrid))
        }
        initAdapter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val anime = (anime_list.adapter as AnimeAdapter).getAnime()
        val listAnime = ListAnime(anime)
        if(anime.isNotEmpty()){
            outState.putParcelable(KEY_ANIME, listAnime.copy(data = anime))
        }
    }

    private fun initAdapter() {
        if(anime_list.adapter == null){
            Log.d("NFragment", "adapter null")
            anime_list.adapter = AnimeAdapter()
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
        val view = inflater.inflate(R.layout.fragment_popular_anime, container, false)
        return view
    }

    fun addAnime(anime: List<AnimeData>){
        (anime_list.adapter as AnimeAdapter).addAnime(anime)
    }

    fun setLayoutManager(layoutGrid: GridLayoutManager): GridLayoutManager{

        val layout = layoutGrid
        val orientation = resources.configuration.orientation

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            layout.spanCount = 6
        }else{
            layout.spanCount = 3
        }

        layout.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                when (anime_list.adapter.getItemViewType(position)) {
                    AdapterConstants.LOADING ->{
                        return layout.spanCount
                    }
                    else ->{
                        return 1
                    }
                }
            }
        })

        return layout
    }

    companion object {
        private const val KEY_ANIME = "anime"
    }
}// Required empty public constructor
