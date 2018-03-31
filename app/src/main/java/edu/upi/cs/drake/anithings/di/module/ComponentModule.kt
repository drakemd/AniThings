package edu.upi.cs.drake.anithings.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import edu.upi.cs.drake.anithings.view.detail.AnimeDetail
import edu.upi.cs.drake.anithings.view.animelist.AnimeListActivity
import edu.upi.cs.drake.anithings.view.animelist.AnimeListFragment

/**
 * Created by drake on 3/27/2018.
 *
 */

@Module
abstract class ComponentModule {
    @ContributesAndroidInjector
    internal abstract fun bindPopularAnimeActivity(): AnimeListActivity

    @ContributesAndroidInjector
    internal abstract fun bindPopularAnimeFragment(): AnimeListFragment

    @ContributesAndroidInjector
    internal abstract fun bindAnimeDetailActivity(): AnimeDetail
}