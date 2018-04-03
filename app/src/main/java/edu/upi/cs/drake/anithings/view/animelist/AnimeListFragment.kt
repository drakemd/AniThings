package edu.upi.cs.drake.anithings.view.animelist


import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.AndroidSupportInjection
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.common.InfiniteScrollListener
import edu.upi.cs.drake.anithings.common.adapter.AdapterConstants
import edu.upi.cs.drake.anithings.common.adapter.AnimeAdapter
import edu.upi.cs.drake.anithings.view.detail.AnimeDetail
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.databinding.FragmentAnimeListBinding
import edu.upi.cs.drake.anithings.viewmodel.ViewModelFactory
import edu.upi.cs.drake.anithings.viewmodel.AnimeListViewModel
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class AnimeListFragment : Fragment(), AnimeListCallback {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var dataBinding: FragmentAnimeListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeListViewModel = ViewModelProviders.of(this, viewModelFactory).get(AnimeListViewModel::class.java)
        animeListViewModel.checkGenre()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_anime_list, container, false)
        val gridLayoutManager = setLayoutManager(GridLayoutManager(activity, 2), dataBinding.animeList)
        dataBinding.animeList.apply {
            layoutManager = gridLayoutManager
            adapter = AnimeAdapter(this@AnimeListFragment)
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener( {animeListViewModel.nextPage()} , gridLayoutManager))
        }
        return dataBinding.root
    }

    override fun onAnimeClick(anime: AnimeEntity?, sharedView: View) {
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity as Activity, sharedView, getString(R.string.transition_thumbnail))
        startActivity(AnimeDetail.newIntent(activity as Activity, anime?.id?:0), options.toBundle())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        animeListViewModel.stateLiveData.observe(this, Observer {
            dataBinding.resource = it
            it?.data?.size?.let { it1 -> animeListViewModel.setPage(it1) }
        })
    }

    private fun setLayoutManager(layoutGrid: GridLayoutManager, recyclerView: RecyclerView): GridLayoutManager{

        val orientation = resources.configuration.orientation
        layoutGrid.spanCount = if(orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3

        layoutGrid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (recyclerView.adapter.getItemViewType(position)) {
                    AdapterConstants.LOADING -> layoutGrid.spanCount
                    else -> 1
                }
            }
        }
        return layoutGrid
    }

    override fun onDestroy() {
        super.onDestroy()
        animeListViewModel.onDestroy()
    }
}// Required empty public constructor
