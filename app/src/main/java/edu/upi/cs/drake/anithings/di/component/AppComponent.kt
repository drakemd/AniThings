package edu.upi.cs.drake.anithings.di.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import edu.upi.cs.drake.anithings.AniThingsApp
import edu.upi.cs.drake.anithings.common.domain.UseCaseModule
import edu.upi.cs.drake.anithings.di.module.AppModule
import edu.upi.cs.drake.anithings.di.module.ComponentModule
import edu.upi.cs.drake.anithings.di.module.NetworkModule
import edu.upi.cs.drake.anithings.data.api.ApiModule
import javax.inject.Singleton

/**
 * Created by drake on 3/27/2018.
 * declare modules used globally by Application
 */

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ComponentModule::class,
    NetworkModule::class,
    ApiModule::class,
    UseCaseModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app: AniThingsApp): Builder
        fun build(): AppComponent
    }
    fun inject(app: AniThingsApp)
}