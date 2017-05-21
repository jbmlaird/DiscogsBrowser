package bj.discogsbrowser.model.user

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class User(val id: String, val username: String, @SerializedName("resource_url") val resourceUrl: String,
                @SerializedName("consumer_name") val consumerName: String)