package edu.upi.cs.drake.anithings.data.api

import dagger.Module
import dagger.Provides
import edu.upi.cs.drake.anithings.data.AnimeRepository
import edu.upi.cs.drake.anithings.data.Repository
import edu.upi.cs.drake.anithings.data.local.LocalAnimeDatasource
import javax.inject.Singleton

/**
 * Created by drake on 3/27/2018.
 * provide Api services
 */

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(remoteAnimeDataSource: RemoteAnimeDataSource,
                               localAnimeDatasource: LocalAnimeDatasource):
            Repository = AnimeRepository(remoteAnimeDataSource, localAnimeDatasource)
}