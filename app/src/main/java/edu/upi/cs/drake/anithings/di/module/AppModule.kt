package edu.upi.cs.drake.anithings.di.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import edu.upi.cs.drake.anithings.AniThingsApp
import edu.upi.cs.drake.anithings.data.AnimeRepository
import edu.upi.cs.drake.anithings.data.local.LocalAnimeDatasource
import edu.upi.cs.drake.anithings.viewmodel.ViewModelFactory
import javax.inject.Singleton

/**
 * Created by drake on 3/27/2018.
 *
 */

@Module
class AppModule {
    @Singleton
    @Provides
    fun provideApplicationContext(app: AniThingsApp): Context{
        return app.applicationContext
    }

    @Provides
    fun provideViewModelFactiory(animeRepository: AnimeRepository): ViewModelFactory {
        return ViewModelFactory(animeRepository)
    }
}