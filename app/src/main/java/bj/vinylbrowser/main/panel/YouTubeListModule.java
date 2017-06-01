package bj.vinylbrowser.main.panel;

import android.content.Context;

import bj.vinylbrowser.di.scopes.ChildFragmentScope;
import bj.vinylbrowser.main.MainPresenter;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 01/06/2017.
 */
@Module
public class YouTubeListModule
{
    private YouTubeListFragment mFragment;

    public YouTubeListModule(YouTubeListFragment fragment)
    {
        mFragment = fragment;
    }

    @Provides
    @ChildFragmentScope
    protected YouTubePlayerEpxController provideYouTubePlayerEpxController(Context context, YouTubePlayerHolder youTubePlayerHolder, MainPresenter mainPresenter)
    {
        return new YouTubePlayerEpxController(context, youTubePlayerHolder, mainPresenter);
    }

    @Provides
    @ChildFragmentScope
    protected YouTubePlayerPresenter provideYoutubePlayerPresenter(YouTubePlayerEpxController controller, YouTubePlayerHolder youTubePlayerHolder)
    {
        return new YouTubePlayerPresenter(controller, youTubePlayerHolder);
    }
}
