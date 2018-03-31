package edu.upi.cs.drake.anithings.view

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<DB: ViewDataBinding>: AppCompatActivity(), HasSupportFragmentInjector{

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    lateinit var databinding: DB

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState, persistentState)
        databinding = DataBindingUtil.setContentView(this, getLayoutRes())
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}