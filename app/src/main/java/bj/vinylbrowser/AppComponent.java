package bj.vinylbrowser;

import javax.inject.Singleton;

import bj.vinylbrowser.artist.ArtistComponent;
import bj.vinylbrowser.artistreleases.ArtistReleasesComponent;
import bj.vinylbrowser.first.FirstComponent;
import bj.vinylbrowser.greendao.DaoModule;
import bj.vinylbrowser.label.LabelComponent;
import bj.vinylbrowser.login.LoginComponent;
import bj.vinylbrowser.main.MainComponent;
import bj.vinylbrowser.marketplace.MarketplaceComponent;
import bj.vinylbrowser.master.MasterComponent;
import bj.vinylbrowser.order.OrderComponent;
import bj.vinylbrowser.release.ReleaseComponent;
import bj.vinylbrowser.search.SearchComponent;
import bj.vinylbrowser.singlelist.SingleListComponent;
import bj.vinylbrowser.utils.UtilsModule;
import bj.vinylbrowser.utils.analytics.AnalyticsModule;
import bj.vinylbrowser.wrappers.WrappersModule;
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

    FirstComponent.Builder firstComponentBuilder();

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
