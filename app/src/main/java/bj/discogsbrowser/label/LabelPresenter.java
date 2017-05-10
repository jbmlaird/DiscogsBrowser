package bj.discogsbrowser.label;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;
import javax.inject.Singleton;

import bj.discogsbrowser.network.DiscogsInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@Singleton
public class LabelPresenter implements LabelContract.Presenter
{
    private final String TAG = getClass().getSimpleName();
    private LabelController controller;
    private DiscogsInteractor discogsInteractor;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public LabelPresenter(@NonNull LabelController controller, @NonNull DiscogsInteractor discogsInteractor,
                          @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.controller = controller;
        this.discogsInteractor = discogsInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
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
    public void getReleaseAndLabelDetails(String id)
    {
        discogsInteractor.fetchLabelDetails(id)
                .doOnSubscribe(onSubscribe -> controller.setLoading(true))
                .subscribeOn(mySchedulerProvider.ui())
                .flatMap(label ->
                {
                    controller.setLabel(label);
                    return discogsInteractor.fetchLabelReleases(id)
                            .subscribeOn(mySchedulerProvider.io());
                })
                .observeOn(mySchedulerProvider.ui())
                .subscribe(labelReleases ->
                        controller.setLabelReleases(labelReleases), error ->
                        controller.setError(true));
    }
}
