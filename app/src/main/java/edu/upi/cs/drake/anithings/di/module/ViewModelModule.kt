package edu.upi.cs.drake.anithings.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import edu.upi.cs.drake.anithings.viewmodel.AnimeDetailViewModel
import edu.upi.cs.drake.anithings.viewmodel.AnimeListViewModel
import edu.upi.cs.drake.anithings.viewmodel.ViewModelFactory

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AnimeListViewModel::class)
    internal abstract fun bindAnimeListViewModel(animeListViewModel: AnimeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnimeDetailViewModel::class)
    internal abstract fun bindAnimeDetailViewModel(animeDetailViewModel: AnimeDetailViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
