package bj.vinylbrowser.model.order

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.common.Price
import bj.vinylbrowser.model.listing.ListingRelease

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class Item(val release: ListingRelease, val price: Price, val id: String) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(release, 0)
        dest.writeParcelable(price, 0)
        dest.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }
}