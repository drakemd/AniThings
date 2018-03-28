package edu.upi.cs.drake.anithings.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import edu.upi.cs.drake.anithings.view.PopularAnimeActivity
import edu.upi.cs.drake.anithings.view.PopularAnimeFragment

/**
 * Created by drake on 3/27/2018.
 *
 */

@Module
abstract class ComponentModule {
    @ContributesAndroidInjector
    internal abstract fun bindPopularAnimeActivity(): PopularAnimeActivity

    @ContributesAndroidInjector
    internal abstract fun bindPopularAnimeFragment(): PopularAnimeFragment
}