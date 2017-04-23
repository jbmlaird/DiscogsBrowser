package bj.rxjavaexperimentation.label;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
public class LabelPresenter implements LabelContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private LabelController controller;
    private CompositeDisposable compositeDisposable;
    private SearchDiscogsInteractor searchDiscogsInteractor;
    private MySchedulerProvider mySchedulerProvider;
    private LogWrapper log;

    @Inject
    public LabelPresenter(@NonNull LabelController controller, @NonNull CompositeDisposable compositeDisposable, @NonNull SearchDiscogsInteractor searchDiscogsInteractor,
                          @NonNull MySchedulerProvider mySchedulerProvider, @NonNull LogWrapper log)
    {
        this.controller = controller;
        this.compositeDisposable = compositeDisposable;
        this.searchDiscogsInteractor = searchDiscogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
        this.log = log;
    }

    @Override
    public void setupRecyclerView(Context context, RecyclerView recyclerView, String title)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(controller.getAdapter());
        controller.setTitle(title);
        controller.requestModelBuild();
    }

    @Override
    public void getData(String id)
    {
        compositeDisposable.add(searchDiscogsInteractor.fetchLabelDetails(id)
                .subscribeOn(mySchedulerProvider.io())
                .observeOn(mySchedulerProvider.ui())
                .doOnComplete(() ->
                        searchDiscogsInteractor.fetchLabelReleases(id)
                                .subscribeOn(mySchedulerProvider.io())
                                .observeOn(mySchedulerProvider.ui())
                                .subscribe(labelReleases ->
                                        controller.setLabelReleases(labelReleases), Throwable::printStackTrace))
                .subscribe(label ->
                {
                    controller.setLabel(label);
                    log.e(TAG, label.getName());
                }, error ->
                {
                    log.e(TAG, "onFetchLabelDetails");
                    error.printStackTrace();
                }));
    }
}
