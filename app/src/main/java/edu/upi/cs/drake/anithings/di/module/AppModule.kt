package edu.upi.cs.drake.anithings.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import edu.upi.cs.drake.anithings.AniThingsApp
import javax.inject.Singleton

/**
 * Created by drake on 3/27/2018.
 *
 */

@Module(includes = [ViewModelModule::class, NetworkModule::class, RepositoryModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideApplicationContext(app: AniThingsApp): Context{
        return app.applicationContext
    }
}