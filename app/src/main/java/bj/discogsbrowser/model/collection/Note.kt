package bj.discogsbrowser.model.collection

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Note(@SerializedName("field_id") val fieldId: Int, val value: String)