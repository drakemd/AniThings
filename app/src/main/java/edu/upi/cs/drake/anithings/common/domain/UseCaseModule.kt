package edu.upi.cs.drake.anithings.common.domain

import dagger.Module
import dagger.Provides
import edu.upi.cs.drake.anithings.data.IAnimeDbService

/**
 * Created by drake on 3/27/2018.
 *
 */

@Module
class UseCaseModule {
    @Provides
    fun providePopularAnimeUseCase(animeDbService: IAnimeDbService): IPopularAnimeUseCase = PopularAnimeUseCase(animeDbService)
}