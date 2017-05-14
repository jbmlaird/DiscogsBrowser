package bj.discogsbrowser.release;

import android.content.Context;

import bj.discogsbrowser.epoxy.release.CollectionWantlistPresenter;
import bj.discogsbrowser.scopes.ActivityScope;
import bj.discogsbrowser.greendao.DaoManager;
import bj.discogsbrowser.network.CollectionWantlistInteractor;
import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.network.LabelInteractor;
import bj.discogsbrowser.utils.analytics.AnalyticsTracker;
import bj.discogsbrowser.utils.ArtistsBeautifier;
import bj.discogsbrowser.utils.ImageViewAnimator;
import bj.discogsbrowser.utils.SharedPrefsManager;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import bj.discogsbrowser.wrappers.ToastyWrapper;
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
    ReleaseContract.View provideReleaseView()
    {
        return mView;
    }

    @Provides
    @ActivityScope
    CollectionWantlistPresenter provideCollectionWantlistPresenter(Context context, CollectionWantlistInteractor interactor, SharedPrefsManager sharedPrefsManager,
                                                                   MySchedulerProvider mySchedulerProvider, ToastyWrapper toastyWrapper)
    {
        return new CollectionWantlistPresenter(context, interactor, sharedPrefsManager, mySchedulerProvider, toastyWrapper);
    }

    @Provides
    @ActivityScope
    ReleaseController provideController(Context context, ArtistsBeautifier artistsBeautifier, ImageViewAnimator imageViewAnimator, CollectionWantlistPresenter presenter,
                                        AnalyticsTracker tracker)
    {
        return new ReleaseController(context, mView, artistsBeautifier, imageViewAnimator, presenter, tracker);
    }


    @Provides
    @ActivityScope
    ReleasePresenter provideReleasePresenter(ReleaseController controller, DiscogsInteractor interactor, LabelInteractor labelInteractor, CollectionWantlistInteractor collectionWantlistInteractor,
                                             MySchedulerProvider mySchedulerProvider, DaoManager daoManager, ArtistsBeautifier artistsBeautifier)
    {
        return new ReleasePresenter(controller, interactor, labelInteractor, collectionWantlistInteractor, mySchedulerProvider, daoManager, artistsBeautifier);
    }
}
