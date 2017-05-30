package bj.vinylbrowser.main

import bj.vinylbrowser.di.scopes.ActivityScope
import dagger.Subcomponent

/**
 * Created by Josh Laird on 29/05/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        fun firstActivityModule(module: MainModule): Builder

        fun build(): MainComponent
    }
}