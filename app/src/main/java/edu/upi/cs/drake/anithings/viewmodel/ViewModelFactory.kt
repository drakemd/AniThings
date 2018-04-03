package edu.upi.cs.drake.anithings.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

import javax.inject.Provider
import javax.inject.Singleton


/**
 * Created by drake on 3/27/2018.
 * Helper ViewModelFactory for generating ViewModels
 * link: https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample
 */

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory @Inject
constructor(var creators: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class " + modelClass)
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}