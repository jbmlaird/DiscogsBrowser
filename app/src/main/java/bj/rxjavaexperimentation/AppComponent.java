package bj.rxjavaexperimentation;

import android.content.Context;

import dagger.Component;

/**
 * Created by j on 18/02/2017.
 */

@Component(modules = AppModule.class)
public interface AppComponent
{
    void inject(App app);

    Context getContext();
}
