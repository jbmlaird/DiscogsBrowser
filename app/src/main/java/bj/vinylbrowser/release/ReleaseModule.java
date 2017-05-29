package bj.vinylbrowser.release;

import android.content.Context;

import bj.vinylbrowser.epoxy.release.CollectionWantlistPresenter;
import bj.vinylbrowser.greendao.DaoManager;
import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.utils.ArtistsBeautifier;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.SharedPrefsManager;
import bj.vinylbrowser.utils.analytics.AnalyticsTracker;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.vinylbrowser.wrappers.ToastyWrapper;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Module
public class ReleaseModule
{
    private ReleaseContract.View mView;

    public ReleaseModule(ReleaseContract.View view)
    {
        mView = view;
    }

    @Provides
    @ActivityScope
    protected ReleaseContract.View provideReleaseView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    protected CollectionWantlistPresenter provideCollectionWantlistPresenter(Context context, DiscogsInteractor interactor, SharedPrefsManager sharedPrefsManager,
                                                                             MySchedulerProvider mySchedulerProvider, ToastyWrapper toastyWrapper)
    {
        return new CollectionWantlistPresenter(context, interactor, sharedPrefsManager, mySchedulerProvider, toastyWrapper);
    }

    @Provides
    @ActivityScope
    protected ReleaseEpxController provideController(Context context, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator, CollectionWantlistPresenter presenter,
                                                     AnalyticsTracker tracker)
    {
        return new ReleaseEpxController(context, mView, artistsBeautifier, imageViewAnimator, presenter, tracker);
    }


    @Provides
    @ActivityScope
    protected ReleasePresenter provideReleasePresenter(ReleaseEpxController controller, DiscogsInteractor interactor,
                                                       MySchedulerProvider mySchedulerProvider, DaoManager daoManager, ArtistsBeautifier artistsBeautifier)
    {
        return new ReleasePresenter(controller, interactor, mySchedulerProvider, daoManager, artistsBeautifier);
    }
}
