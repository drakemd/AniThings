package edu.upi.cs.drake.anithings.di.module

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import edu.upi.cs.drake.anithings.AniThingsApp
import edu.upi.cs.drake.anithings.data.AnimeRepository
import edu.upi.cs.drake.anithings.data.Repository
import edu.upi.cs.drake.anithings.data.remote.RemoteAnimeDataSource
import edu.upi.cs.drake.anithings.data.remote.RemoteAnimeService
import edu.upi.cs.drake.anithings.data.local.LocalAnimeDatasource
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRemoteAnimeService(retrofit: Retrofit): RemoteAnimeService {
        return retrofit.create(RemoteAnimeService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: AniThingsApp): LocalAnimeDatasource {
        return Room.databaseBuilder(app, LocalAnimeDatasource::class.java, "anime-db").build()
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(remoteAnimeDataSource: RemoteAnimeDataSource,
                               localAnimeDatasource: LocalAnimeDatasource):
            Repository = AnimeRepository(remoteAnimeDataSource, localAnimeDatasource)
}