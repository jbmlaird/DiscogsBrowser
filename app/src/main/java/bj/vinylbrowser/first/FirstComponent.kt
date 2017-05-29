package bj.vinylbrowser.first

import bj.vinylbrowser.di.scopes.ActivityScope
import dagger.Subcomponent

/**
 * Created by Josh Laird on 29/05/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(FirstModule::class))
interface FirstComponent {
    fun inject(firstActivity: FirstActivity)

    @Subcomponent.Builder
    interface Builder {
        fun firstActivityModule(module: FirstModule): Builder

        fun build(): FirstComponent
    }
}