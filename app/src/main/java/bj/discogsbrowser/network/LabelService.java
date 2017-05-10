package bj.discogsbrowser.network;

import bj.discogsbrowser.model.label.Label;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Josh Laird on 10/05/2017.
 */
public interface LabelService
{
    @GET("labels/{label_id}")
    Single<Label> getLabel(@Path("label_id") String labelId);
}
