package bj.vinylbrowser.model.release

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Company(val catno: String, @SerializedName("entity_type") val entityType: String,
                   @SerializedName("entity_type_name") val entityTypeName: String, val id: Int,
                   val name: String, @SerializedName("resource_url") val resourceUrl: String)