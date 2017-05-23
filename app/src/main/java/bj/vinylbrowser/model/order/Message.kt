package bj.vinylbrowser.model.order

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Message(val refund: Refund, val timestamp: String, val message: String, val type: String,
                   val order: Order, val subject: String, val from: From, @SerializedName("status_id") val statusId: Int,
                   val actor: Actor, val original: Int, val new: Int)