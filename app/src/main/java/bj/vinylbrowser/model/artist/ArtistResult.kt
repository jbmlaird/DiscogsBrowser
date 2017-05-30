package bj.vinylbrowser.model.artist

import android.os.Parcel
import android.os.Parcelable
import bj.vinylbrowser.model.release.Image
import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 18/05/2017.
 */
data class ArtistResult(@SerializedName("namevariations") var nameVariations: List<String> = emptyList(),
                        @SerializedName("profile") var profile: String = "",
                        @SerializedName("releases_url") var releasesUrl: String = "",
                        @SerializedName("uri") var uri: String = "",
                        @SerializedName("urls") var urls: List<String> = emptyList(),
                        @SerializedName("data_quality") var dataQuality: String = "",
                        @SerializedName("id") var id: String = "",
                        @SerializedName("images") var images: List<Image> = emptyList(),
                        @SerializedName("members") var members: List<Member> = emptyList(),
                        var artistWantedUrls: List<ArtistWantedUrl> = emptyList()) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeStringList(nameVariations)
        dest.writeString(profile)
        dest.writeString(releasesUrl)
        dest.writeString(uri)
        dest.writeStringList(urls)
        dest.writeString(dataQuality)
        dest.writeString(id)
        dest.writeList(images)
        dest.writeList(members)
        dest.writeList(artistWantedUrls)
    }

    override fun describeContents(): Int {
        return 0
    }
}