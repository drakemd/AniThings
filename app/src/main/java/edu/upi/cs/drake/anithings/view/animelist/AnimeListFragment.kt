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
 * this fragment is responsible to display anime data obtained from the view model
 * this fragment also responsible to customize request parameters input to the API
 */
class AnimeListFragment : Fragment(), AnimeListCallback{

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var animeListViewModel: AnimeListViewModel
    private lateinit var dataBinding: FragmentAnimeListBinding

    //initialize viewmodel and check if the genres database already populated
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeListViewModel = ViewModelProviders.of(this, viewModelFactory).get(AnimeListViewModel::class.java)
        animeListViewModel.checkGenre()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    //inflate the layout by data binding and set recyclerView layout, adapter and scroll listener
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_anime_list, container, false)
        val gridLayoutManager = setLayoutManager(GridLayoutManager(activity, 2), dataBinding.animeList)
        dataBinding.animeList.apply {
            layoutManager = gridLayoutManager
            adapter = AnimeAdapter(this@AnimeListFragment)
            clearOnScrollListeners()

            //nextPage will be called if we scroll to the end of the list
            addOnScrollListener(InfiniteScrollListener( {animeListViewModel.nextPage()} , gridLayoutManager))
        }
        return dataBinding.root
    }

    //this method set the span size of the item depending on its type and orientation
    private fun setLayoutManager(layoutGrid: GridLayoutManager, recyclerView: RecyclerView): GridLayoutManager{

        val orientation = resources.configuration.orientation
        //set total column depending on device orientation
        layoutGrid.spanCount = if(orientation == Configuration.ORIENTATION_LANDSCAPE) 5 else 3

        //specify span size depending on items ViewType
        layoutGrid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (recyclerView.adapter.getItemViewType(position)) {
                    AdapterConstants.ANIME -> 1
                    else -> layoutGrid.spanCount
                }
            }
        }
        return layoutGrid
    }

    //will start AnimeDetail when anime item is clicked
    override fun onAnimeClick(anime: AnimeEntity?, sharedView: View) {
        //set transition animation
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity as Activity, sharedView, getString(R.string.transition_thumbnail))
        startActivity(AnimeDetail.newIntent(activity as Activity, anime?.id?:0), options.toBundle())
    }

    //when the user click retry button if there is an error in connection
    override fun onConnectionError(){
        //remove error notification and replace it with loading item
        (dataBinding.animeList.adapter as AnimeAdapter).removeLastItem()
        (dataBinding.animeList.adapter as AnimeAdapter).showLoading()
        //check if genres already downloaded and resubscribe the repo
        animeListViewModel.checkGenre()
        animeListViewModel.unSubscribeRepo()
        animeListViewModel.subscribeRepo()
    }

    //set up the mode and bind the data from the view model to the layout
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListModeView(savedInstanceState)
        animeListViewModel.stateLiveData.observe(this, Observer {
            dataBinding.resource = it
            it?.data?.size?.let { it1 -> animeListViewModel.setPage(it1)}
        })
    }

    //this method set up view model attribute to request anime from the API
    private fun setListModeView(savedInstanceState: Bundle?){
        //get values from arguments
        val delete =  arguments?.getBoolean(DELETE_KEY)?:false
        val mode = arguments?.getInt(MODE_KEY)?:1
        val search = arguments?.getString(MODE_TEXT)

        //modify sharedPreferences with new values
        val sharedPreferences =
                activity?.getSharedPreferences(getString(R.string.edu_upi_cs_drake_anithing_PREFERENCE_KEY), Context.MODE_PRIVATE)
        sharedPreferences?.edit().apply {
            this?.putInt(getString(R.string.shared_preference_anime), mode)
            this?.putString(getString(R.string.shared_preference_anime_filter), search)
            this?.apply()
        }

        //setting up view model attribute that will be used as parameters to made new request to the API
        animeListViewModel.setMode(mode)
        animeListViewModel.setSearchFilter(search)
        (activity as AnimeListActivity).setTitleName(mode)
        if(delete && savedInstanceState == null){
            animeListViewModel.unSubscribeRepo()
            animeListViewModel.deleteAllAnime()
        }else{
            animeListViewModel.unSubscribeRepo()
            animeListViewModel.subscribeRepo()
        }
    }

    //saved instance state so the list wont change even if the screen rotate
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MODE_KEY, animeListViewModel.getMode())
    }

    override fun onDestroy() {
        super.onDestroy()
        animeListViewModel.unSubscribeRepo()
    }

    companion object {
        /*
        * 1 = most popular anime
        * 2 = highest rating anime
        * 3 = current anime
        * 4 = search text
        * */

        const val MODE_KEY = "MODE_KEY"
        const val MODE_TEXT = "MODE_TEXT"
        private const val DELETE_KEY = "DELETE_KEY"

        //static method to create new instance with the specified arguments
        fun newInstance(newMode: Int, previousMode: Int, newFilter: String?, previousFilter: String?): AnimeListFragment{
            val newFrag = AnimeListFragment()
            val args = Bundle()
            //specify if the database should be deleted or not
            val delete = (newMode != previousMode) || (newFilter != previousFilter && newFilter != null)
            args.putInt(MODE_KEY, newMode)
            args.putString(MODE_TEXT, newFilter)
            args.putBoolean(DELETE_KEY, delete)
            newFrag.arguments = args
            return newFrag
        }
    }
}
