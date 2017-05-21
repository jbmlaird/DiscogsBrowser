package bj.discogsbrowser.model.release

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Community(var contributors: List<Contributor> = emptyList(),
                     @SerializedName("data_quality") var dataQuality: String = "",
                     var have: Int = 0,
                     var rating: Rating = Rating(0.0, 0),
                     var status: String = "",
                     var submitter: Submitter = Submitter("", ""),
                     var want: Int = 0)