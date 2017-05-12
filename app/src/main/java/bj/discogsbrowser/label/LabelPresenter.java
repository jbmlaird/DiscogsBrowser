package bj.discogsbrowser.label;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import bj.discogsbrowser.ActivityScope;
import bj.discogsbrowser.network.LabelInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 */
@ActivityScope
public class LabelPresenter implements LabelContract.Presenter
{
    private LabelController controller;
    private LabelInteractor labelInteractor;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public LabelPresenter(@NonNull LabelController controller, @NonNull LabelInteractor labelInteractor,
                          @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.controller = controller;
        this.labelInteractor = labelInteractor;
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
        labelInteractor.fetchLabelDetails(id)
                .doOnSubscribe(onSubscribe -> controller.setLoading(true))
                .subscribeOn(mySchedulerProvider.ui())
                .flatMap(label ->
                {
                    controller.setLabel(label);
                    return labelInteractor.fetchLabelReleases(id)
                            .subscribeOn(mySchedulerProvider.io());
                })
                .observeOn(mySchedulerProvider.ui())
                .subscribe(labelReleases ->
                        controller.setLabelReleases(labelReleases), error ->
                        controller.setError(true));
    }
}
