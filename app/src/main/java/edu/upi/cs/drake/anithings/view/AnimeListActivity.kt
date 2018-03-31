package edu.upi.cs.drake.anithings.view

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import edu.upi.cs.drake.anithings.R
import javax.inject.Inject

class AnimeListActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_anime)

        if(savedInstanceState == null){
            changeFragment(AnimeListFragment())
        }
    }

    @SuppressLint("PrivateResource")
    fun changeFragment(fragment: Fragment, cleanStack: Boolean = false){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (cleanStack){
            clearBackStack()
        }

        fragmentTransaction.replace(R.id.activity_base_content, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun clearBackStack() {
        val manager = supportFragmentManager
        if(manager.backStackEntryCount > 0){
            val first = manager.getBackStackEntryAt(0)
            manager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager

        if(fragmentManager.backStackEntryCount > 1){
            fragmentManager.popBackStack()
        }else{
            finish()
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
