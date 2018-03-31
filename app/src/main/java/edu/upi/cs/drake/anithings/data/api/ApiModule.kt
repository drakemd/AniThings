package edu.upi.cs.drake.anithings.data.api

import dagger.Module
import dagger.Provides
import edu.upi.cs.drake.anithings.data.IAnimeDbService
import javax.inject.Singleton

/**
 * Created by drake on 3/27/2018.
 * provide Api services
 */

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(animeApi: IAnimeApi): IAnimeDbService = AnimeDbService(animeApi)
}