package bj.discogsbrowser.network;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import bj.discogsbrowser.model.release.Label;
import bj.discogsbrowser.utils.schedulerprovider.MySchedulerProvider;
import io.rx_cache2.DynamicKey;
import retrofit2.Retrofit;

/**
 * Created by Josh Laird on 10/05/2017.
 */

public class LabelInteractor
{
    LabelService labelService;
    private CacheProviders cacheProviders;
    private MySchedulerProvider mySchedulerProvider;

    @Inject
    public LabelInteractor(Retrofit retrofit, CacheProviders cacheProviders, MySchedulerProvider mySchedulerProvider)
    {
        labelService = retrofit.create(LabelService.class);
        this.cacheProviders = cacheProviders;
        this.mySchedulerProvider = mySchedulerProvider;
    }

    public void getReleaseLabelDetails(List<Label> labels, String labelId)
    {
        for (Label releaseLabel : labels)
        {
            cacheProviders.fetchLabelDetails(labelService.getLabel(labelId), new DynamicKey(labelId))
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
                    })
                    .subscribe(labelDetails ->
                            {
                                if (labelDetails.getImages() != null && labelDetails.getImages().size() > 0)
                                    releaseLabel.setThumb(labelDetails.getImages().get(0).getUri());
                            },
                            error ->
                                    Log.e("LabelInteractor", "Unable to get label details"));
        }

    }
}
