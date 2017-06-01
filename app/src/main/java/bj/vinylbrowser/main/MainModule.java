package bj.vinylbrowser.main;

import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.main.panel.YouTubeListFragment;
import bj.vinylbrowser.main.panel.YouTubePlayerHolder;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 01/06/2017.
 */
@Module
public class MainModule
{
    private final MainContract.View mView;

    public MainModule(MainContract.View view)
    {
        mView = view;
    }

    @Provides
    @ActivityScope
    protected MainContract.View provideView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    protected YouTubePlayerHolder provideYoutubePlayerHolder()
    {
        return new YouTubePlayerHolder();
    }

    @Provides
    @ActivityScope
    protected MainPresenter provideMainPresenter()
    {
        return new MainPresenter(mView);
    }

    @Provides
    @ActivityScope
    protected YouTubeListFragment provideYouTubeListFragment()
    {
        return new YouTubeListFragment();
    }

    @Provides
    protected RouterAttacher provideRouterAttacher()
    {
        return new RouterAttacher();
    }
}
