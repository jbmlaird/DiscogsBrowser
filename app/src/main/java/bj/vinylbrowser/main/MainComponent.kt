package bj.vinylbrowser.main

import bj.vinylbrowser.artist.ArtistComponent
import bj.vinylbrowser.artistreleases.ArtistReleasesComponent
import bj.vinylbrowser.di.scopes.ActivityScope
import bj.vinylbrowser.home.HomeComponent
import bj.vinylbrowser.label.LabelComponent
import bj.vinylbrowser.main.panel.YouTubeListComponent
import bj.vinylbrowser.marketplace.MarketplaceComponent
import bj.vinylbrowser.master.MasterComponent
import bj.vinylbrowser.order.OrderComponent
import bj.vinylbrowser.release.ReleaseComponent
import bj.vinylbrowser.search.SearchComponent
import bj.vinylbrowser.singlelist.SingleListComponent
import dagger.Subcomponent

/**
 * Created by Josh Laird on 29/05/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(mainActivity: MainActivity)

    fun youTubeListComponentBuilder(): YouTubeListComponent.Builder

    fun artistComponentBuilder(): ArtistComponent.Builder

    fun artistReleasesComponentBuilder(): ArtistReleasesComponent.Builder

    fun labelComponentBuilder(): LabelComponent.Builder

    fun homeComponentBuilder(): HomeComponent.Builder

    fun marketplaceComponentBuilder(): MarketplaceComponent.Builder

    fun masterComponentBuilder(): MasterComponent.Builder

    fun orderComponentBuilder(): OrderComponent.Builder

    fun releaseComponentBuilder(): ReleaseComponent.Builder

    fun searchComponentBuilder(): SearchComponent.Builder

    fun singleListComponentBuilder(): SingleListComponent.Builder

    @Subcomponent.Builder
    interface Builder {
        fun mainActivityModule(module: MainModule): Builder

        fun build(): MainComponent
    }
}