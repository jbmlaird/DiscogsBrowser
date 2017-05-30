package bj.vinylbrowser.model.release

import android.os.Parcel
import android.os.Parcelable
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
                     var want: Int = 0) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(contributors)
        dest.writeString(dataQuality)
        dest.writeInt(have)
        dest.writeParcelable(rating, 0)
        dest.writeString(status)
        dest.writeParcelable(submitter, 0)
        dest.writeInt(want)
    }

    override fun describeContents(): Int {
        return 0
    }
}