package edu.upi.cs.drake.anithings.view


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.InfiniteScrollListener
import edu.upi.cs.drake.anithings.common.adapter.AnimeAdapter
import edu.upi.cs.drake.anithings.common.model.AnimeListState
import edu.upi.cs.drake.anithings.repository.model.AnimeData
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

    private val stateObserver = Observer<AnimeListState> { state ->
        state?.let {
            isLastPage = state.loadedAllItems
            when (state) {
                is AnimeListState.DefaultState -> {
                    isLoading = false
                    addAnime(state.data)
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
        super.onActivityCreated(savedInstanceState)

        anime_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener( {popularAnimeViewModel.updateAnimeList()} , linearLayout))
        }
        initAdapter()
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

}// Required empty public constructor
