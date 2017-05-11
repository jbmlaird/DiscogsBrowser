package bj.discogsbrowser.network;

import bj.discogsbrowser.model.label.Label;
import bj.discogsbrowser.model.labelrelease.RootLabelResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Josh Laird on 10/05/2017.
 */
public interface LabelService
{
    @GET("labels/{label_id}")
    Single<Label> getLabel(@Path("label_id") String labelId);

    @GET("labels/{label_id}/releases")
    Single<RootLabelResponse> getLabelReleases(@Path("label_id") String labelId, @Query("sort_order") String sort, @Query("per_page") String perPage);
}
