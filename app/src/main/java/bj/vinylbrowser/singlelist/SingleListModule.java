package bj.vinylbrowser.singlelist;

import android.content.Context;

import bj.vinylbrowser.network.DiscogsInteractor;
import bj.vinylbrowser.di.scopes.ActivityScope;
import bj.vinylbrowser.utils.FilterHelper;
import bj.vinylbrowser.utils.ImageViewAnimator;
import bj.vinylbrowser.utils.schedulerprovider.MySchedulerProvider;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 16/04/2017.
 */
@Module
public class SingleListModule
{
    private SingleListContract.View view;

    public SingleListModule(SingleListContract.View view)
    {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public SingleListContract.View provideSingleListView()
    {
        return view;
    }

    @Provides
    @ActivityScope
    protected FilterHelper provideFilterHelper()
    {
        return new FilterHelper();
    }

    @Provides
    @ActivityScope
    protected SingleListController provideController(Context context, ImageViewAnimator imageViewAnimator)
    {
        return new SingleListController(context, view, imageViewAnimator);
    }

    @Provides
    @ActivityScope
    protected SingleListPresenter providePresenter(Context context, DiscogsInteractor discogsInteractor, MySchedulerProvider mySchedulerProvider,
                                                   SingleListController controller, FilterHelper filterHelper)
    {
        return new SingleListPresenter(context, view, discogsInteractor, mySchedulerProvider, controller,
                new CompositeDisposable(), filterHelper);
    }
}
