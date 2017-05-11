package bj.discogsbrowser.network;

import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.model.label.Label;
import bj.discogsbrowser.model.labelrelease.LabelRelease;
import bj.discogsbrowser.model.labelrelease.RootLabelResponse;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.reactivex.Single;
import io.rx_cache2.DynamicKey;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 10/05/2017.
 */

public class LabelInteractor
{
    private LabelService labelService;
    private CacheProviders cacheProviders;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public LabelInteractor(Retrofit retrofit, CacheProviders cacheProviders, MySchedulerProvider mySchedulerProvider)
    {
        labelService = retrofit.create(LabelService.class);
        this.cacheProviders = cacheProviders;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    public Single<Label> fetchLabelDetails(String labelId)
    {
        return cacheProviders.fetchLabelDetails(labelService.getLabel(labelId), new DynamicKey(labelId))
                .subscribeOn(mySchedulerProvider.io())
                .map(label ->
                {
                    if (label.getProfile() != null)
                    {
                        label.setProfile(label.getProfile().replace("[a=", ""));
                        label.setProfile(label.getProfile().replace("[i]", ""));
                        label.setProfile(label.getProfile().replace("[/l]", ""));
                        label.setProfile(label.getProfile().replace("[/I]", ""));
                        label.setProfile(label.getProfile().replace("]", ""));
                    }
                    return label;
                });
    }

    public Single<List<LabelRelease>> fetchLabelReleases(String labelId)
    {
        return cacheProviders.fetchLabelReleases(labelService.getLabelReleases(labelId, "desc", "500"), new DynamicKey(labelId))
                .subscribeOn(mySchedulerProvider.io())
                .map(RootLabelResponse::getLabelReleases);
    }
}
