package bj.vinylbrowser;

import javax.inject.Singleton;

import bj.vinylbrowser.greendao.DaoModule;
import bj.vinylbrowser.login.LoginComponent;
import bj.vinylbrowser.main.MainComponent;
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

    LoginComponent.Builder loginComponentBuilder();

    MainComponent.Builder mainComponentBuilder();
}
