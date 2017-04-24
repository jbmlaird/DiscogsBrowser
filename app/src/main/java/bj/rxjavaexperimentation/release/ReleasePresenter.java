package bj.rxjavaexperimentation.release;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.rxjavaexperimentation.network.SearchDiscogsInteractor;
import bj.rxjavaexperimentation.schedulerprovider.MySchedulerProvider;
import bj.rxjavaexperimentation.wrappers.LogWrapper;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class ReleasePresenter implements ReleaseContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private final ReleaseContract.View mView;
    private final ReleaseController controller;
    private final CompositeDisposable compositeDisposable;
    private final SearchDiscogsInteractor searchDiscogsInteractor;
    private final MySchedulerProvider mySchedulerProvider;
    private final LogWrapper log;

    @Inject
    public ReleasePresenter(@NonNull ReleaseContract.View view, @NonNull ReleaseController controller, @NonNull CompositeDisposable compositeDisposable, @NonNull SearchDiscogsInteractor searchDiscogsInteractor,
                            @NonNull MySchedulerProvider mySchedulerProvider, @NonNull LogWrapper log)
    {
        this.mView = view;
        this.controller = controller;
        this.compositeDisposable = compositeDisposable;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.log = log;
    }

    @Override
    public void getData(String id)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchReleaseDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .subscribe(release ->
                        {
                            controller.setRelease(release);
                            log.e(TAG, release.getTitle());
                            if (release.getNumForSale() != 0)
                                searchDiscogsInteractor.getReleaseMarketListings(id, "release")
                                        .subscribeOn(mySchedulerProvider.io())
                                        .observeOn(mySchedulerProvider.ui())
                                        .subscribe(controller::setReleaseListings,
                                                error ->
                                                        controller.setReleaseListingsError()
                                        );
                            else
                                controller.setReleaseListings(new ArrayList<>());
                        }, error ->
                        {
                            log.e(TAG, "onReleaseDetailsError");
                            error.printStackTrace();
                        }
                ));
    }

    @Override
    public void setupRecyclerView(Context context, RecyclerView recyclerView, String title)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(controller.getAdapter());
        controller.setTitle(title);
        controller.requestModelBuild();
    }
}
