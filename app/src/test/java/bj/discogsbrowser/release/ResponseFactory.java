package bj.discogsbrowser.release;

import bj.discogsbrowser.model.collection.AddToCollectionResponse;
import bj.discogsbrowser.model.wantlist.AddToWantlistResponse;
import okhttp3.internal.http.RealResponseBody;
import retrofit2.Response;

/**
 * Created by Josh Laird on 11/05/2017.
 */
public class ResponseFactory
{
    public AddToCollectionResponse getAddToCollectionSuccessfulResponse()
    {
        AddToCollectionResponse addToCollectionResponse = new AddToCollectionResponse();
        addToCollectionResponse.setInstanceId("yeson");
        return addToCollectionResponse;
    }

    public AddToCollectionResponse getAddToCollectionBadResponse()
    {
        AddToCollectionResponse addToCollectionResponse = new AddToCollectionResponse();
        return addToCollectionResponse;
    }

    public Response<Void> getRetrofitSuccessfulResponse()
    {
        return Response.success(null);
    }

    public Response<Void> getRetrofitBadResponse()
    {
        return Response.error(404, new RealResponseBody(null, null));
    }

    public AddToWantlistResponse getAddToWantlistSuccessfulResponse()
    {
        return new AddToWantlistResponse();
    }
}
