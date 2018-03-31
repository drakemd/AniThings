package edu.upi.cs.drake.anithings.di.module

import dagger.Module
import dagger.Provides
import edu.upi.cs.drake.anithings.data.api.IAnimeApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by drake on 3/27/2018.
 *
 */

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideKitsuAnimeService(retrofit: Retrofit): IAnimeApi {
        return retrofit.create(IAnimeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
                .baseUrl("https://kitsu.io/api/edge/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}