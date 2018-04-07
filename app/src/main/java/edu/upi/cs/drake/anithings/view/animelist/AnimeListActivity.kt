package edu.upi.cs.drake.anithings.view.animelist

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.SearchView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import edu.upi.cs.drake.anithings.R
import edu.upi.cs.drake.anithings.databinding.ActivityMainDrawerBinding
import kotlinx.android.synthetic.main.nav_header_main.view.*
import javax.inject.Inject

/**
 * this activity is the main activity of this app
 * this activity has drawer layout and a fragment
 * the drawer layout has 3 items menu to choose from and also has a search bar to search anime
 * the fragment is used to display the recycler view that contains the list of anime
 */
class AnimeListActivity : AppCompatActivity(), HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainDrawerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        //set theme after splash screen end
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        //bind layout, set toolbar and drawer layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_drawer)
        setSupportActionBar(binding.layout1?.toolbarMain)
        setUpDrawerLayout()

        if(savedInstanceState == null){
            startFragment()
        }
    }

    //method to set up drawer layout and item listener also add listener query listener to the searchView
    private fun setUpDrawerLayout(){
        //drawer layout set up and add listener
        toggle =
                ActionBarDrawerToggle(this, binding.drawerLayout, binding.layout1?.toolbarMain, R.string.drawer_open, R.string.drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        binding.navView.setNavigationItemSelectedListener(this)

        //search view listener
        binding.navView.getHeaderView(0).search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!TextUtils.isEmpty(query) && query != null){
                    startFragment(4, query)
                    binding.drawerLayout.closeDrawers()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    //this method start fragment by replacing fragment in the layout
    private fun startFragment(newMode: Int? = null, newFilter: String? = null){
        //previous mode and filter used by the fragment
        val (previousMode, previousFilter) = getPreviousModeAndFilterFromSharedPref()

        //fragment transaction section
        val fragment: Fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        //if new mode not specified then it will use previous mode from sharedPreference to create new instance
        //if new mode not specified means that the method is called the first time by this activity so there is no fragment yet
        //the mode and search filter will be used as parameter to request data from the API
        if(newMode == null){
            fragment = previousMode.let {AnimeListFragment.newInstance(newMode = it, previousMode = it, newFilter = newFilter, previousFilter = previousFilter)}
        }else{
            fragment = previousMode.let { AnimeListFragment.newInstance(newMode = newMode, previousMode = it, newFilter = newFilter, previousFilter = previousFilter)}
            fragmentTransaction.remove(supportFragmentManager.findFragmentById(R.id.activity_base_content))
        }
        fragmentTransaction.replace(R.id.activity_base_content, fragment).commit()
    }

    //set toolbar name depending on current mode
    fun setTitleName(mode: Int){
        var title = "Anithings"
        when(mode){
            1 -> title = "Popular Anime"
            2 -> title = "Top Rated Anime"
            3 -> title = "Currently Airing"
            4 -> title = "Search Result"
        }
        supportActionBar?.title = title
    }

    //this method will get previous mode and search filter from the shared preference, the default value is 1 and null
    private fun getPreviousModeAndFilterFromSharedPref(): Pair<Int, String?>{
        val sharedPreferences: SharedPreferences? =
                getSharedPreferences(getString(R.string.edu_upi_cs_drake_anithing_PREFERENCE_KEY), Context.MODE_PRIVATE)
        val previousMode = sharedPreferences?.getInt(getString(R.string.shared_preference_anime), 1)?:1
        val previousFilter = sharedPreferences?.getString(getString(R.string.shared_preference_anime_filter), null)
        return Pair(previousMode, previousFilter)
    }

    //this navigation item listener  will start a fragment with specific mode
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawers()
        when (item.itemId) {
            R.id.nav_popular_anime -> startFragment(1)
            R.id.nav_top_anime -> startFragment(2)
            R.id.nav_current_anime -> startFragment(3)
        }
        return true
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(binding.navView)){
            binding.drawerLayout.closeDrawers()
        }else{
            val fragmentManager = supportFragmentManager
            if(fragmentManager.backStackEntryCount > 1){
                fragmentManager.popBackStack()
            }else{
                finish()
            }
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
