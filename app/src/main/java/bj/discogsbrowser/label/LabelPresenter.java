package bj.discogsbrowser.label;

import android.support.annotation.NonNull;

import bj.discogsbrowser.network.LabelInteractor;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;

/**
 * Created by Josh Laird on 23/04/2017.
 */
public class LabelPresenter implements LabelContract.Presenter
{
    private LabelController controller;
    private LabelInteractor labelInteractor;
    private MySchedulerProvider mySchedulerProvider;

    public LabelPresenter(@NonNull LabelController controller, @NonNull LabelInteractor labelInteractor,
                          @NonNull MySchedulerProvider mySchedulerProvider)
    {
        this.controller = controller;
        this.labelInteractor = labelInteractor;
        this.mySchedulerProvider = mySchedulerProvider;
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
