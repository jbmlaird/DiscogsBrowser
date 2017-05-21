package bj.discogsbrowser.model.wantlist

import bj.discogsbrowser.model.common.BasicInformation
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class AddToWantlistResponse(var id: String = "",
                                 var rating: Int = 0,
                                 var notes: String = "",
                                 @SerializedName("resource_url") var resourceUrl: String = "",
                                 @SerializedName("basic_information") var basicInformation: BasicInformation = BasicInformation())