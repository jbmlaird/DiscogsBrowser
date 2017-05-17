package bj.discogsbrowser;

import javax.inject.Singleton;

import bj.discogsbrowser.artist.ArtistComponent;
import bj.discogsbrowser.artistreleases.ArtistReleasesComponent;
import bj.discogsbrowser.label.LabelComponent;
import bj.discogsbrowser.login.LoginComponent;
import bj.discogsbrowser.main.MainComponent;
import bj.discogsbrowser.marketplace.MarketplaceComponent;
import bj.discogsbrowser.master.MasterComponent;
import bj.discogsbrowser.order.OrderComponent;
import bj.discogsbrowser.release.ReleaseComponent;
import bj.discogsbrowser.search.SearchComponent;
import bj.discogsbrowser.singlelist.SingleListComponent;
import bj.discogsbrowser.utils.analytics.AnalyticsModule;
import bj.discogsbrowser.greendao.DaoModule;
import bj.discogsbrowser.utils.UtilsModule;
import bj.discogsbrowser.wrappers.WrappersModule;
import dagger.Component;

/**
 * Created by j on 18/02/2017.
 */
@Singleton
@Component(modules = {AppModule.class, WrappersModule.class, AnalyticsModule.class, UtilsModule.class, DaoModule.class})
public interface AppComponent
{
    void inject(App app);

    ArtistComponent.Builder artistComponentBuilder();

    ArtistReleasesComponent.Builder artistReleasesComponentBuilder();

    LabelComponent.Builder labelComponentBuilder();

    LoginComponent.Builder loginComponentBuilder();

    MainComponent.Builder mainComponentBuilder();

    MarketplaceComponent.Builder marketplaceComponentBuilder();

    MasterComponent.Builder masterComponentBuilder();

    OrderComponent.Builder orderComponentBuilder();

    ReleaseComponent.Builder releaseComponentBuilder();

    SearchComponent.Builder searchComponentBuilder();

    SingleListComponent.Builder singleListComponentBuilder();
}
