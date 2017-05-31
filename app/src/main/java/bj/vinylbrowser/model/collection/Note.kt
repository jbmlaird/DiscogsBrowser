package bj.vinylbrowser.model.collection

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Note(@SerializedName("field_id") val fieldId: Int, val value: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(fieldId)
        dest.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }
}