package bj.rxjavaexperimentation;

import android.content.Context;

import com.github.scribejava.core.oauth.OAuth10aService;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by j on 18/02/2017.
 */
@Component(modules = AppModule.class)
public interface AppComponent
{
    void inject(App app);

    Context getContext();

    Retrofit getRetrofit();

    OAuth10aService getOAuthService();
}
