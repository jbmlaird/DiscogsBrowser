package bj.discogsbrowser.release

import bj.discogsbrowser.model.collection.AddToCollectionResponse
import bj.discogsbrowser.model.wantlist.AddToWantlistResponse
import okhttp3.internal.http.RealResponseBody
import retrofit2.Response

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ResponseFactory {
    @JvmStatic fun buildAddToCollectionSuccessResponse(): AddToCollectionResponse {
        return AddToCollectionResponse(instanceId = "yeson")
    }

    @JvmStatic fun buildAddToCollectionBadResponse(): AddToCollectionResponse {
        return AddToCollectionResponse()
    }

    @JvmStatic fun getRetrofitSuccessfulResponse(): Response<Void> {
        return Response.success<Void>(null)
    }

    @JvmStatic fun getRetrofitBadResponse(): Response<Void> {
        return Response.error(404, RealResponseBody(null, null))
    }

    @JvmStatic fun buildAddToWantlistSuccessResponse(): AddToWantlistResponse {
        return AddToWantlistResponse()
    }
}