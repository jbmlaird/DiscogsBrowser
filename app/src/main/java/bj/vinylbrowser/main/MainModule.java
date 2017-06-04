package bj.vinylbrowser.main;

import android.content.Context;

import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.main.panel.YouTubeListFragment;
import bj.vinylbrowser.main.panel.YouTubePlayerEpxController;
import bj.vinylbrowser.main.panel.YouTubePlayerHolder;
import bj.vinylbrowser.main.panel.YouTubePlayerPresenter;
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
    protected YouTubePlayerHolder provideYoutubePlayerHolder(Context context)
    {
        return new YouTubePlayerHolder(context);
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

    @Provides
    @ActivityScope
    protected YouTubePlayerEpxController provideYouTubePlayerEpxController(Context context, YouTubePlayerHolder youTubePlayerHolder, MainPresenter mainPresenter)
    {
        return new YouTubePlayerEpxController(context, youTubePlayerHolder, mainPresenter);
    }

    @Provides
    @ActivityScope
    protected YouTubePlayerPresenter provideYoutubePlayerPresenter(Context context, YouTubePlayerEpxController controller, YouTubePlayerHolder youTubePlayerHolder)
    {
        return new YouTubePlayerPresenter(context, controller, youTubePlayerHolder);
    }
}
