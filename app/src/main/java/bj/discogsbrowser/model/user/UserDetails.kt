package bj.discogsbrowser.model.user

import com.google.gson.annotations.SerializedName

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class UserDetails(var profile: String = "",
                       @SerializedName("wantlist_url") var wantlistUrl: String = "",
                       var rank: Int = 0,
                       @SerializedName("num_pending") var numPending: Int = 0,
                       var id: Int = 0,
                       @SerializedName("num_for_sale") var numForSale: Int = 0,
                       @SerializedName("home_page") var homePage: String = "",
                       var location: String = "",
                       @SerializedName("collection_folders_url") var collectionFoldersUrl: String = "",
                       var username: String = "",
                       @SerializedName("collection_fields_url") var collectionFieldsUrl: String = "",
                       @SerializedName("releases_contributed") var releaseContributed: String = "",
                       var registered: String = "",
                       @SerializedName("rating_avg") var ratingAvg: String = "",
                       @SerializedName("num_collection") var numCollection: Int = 0,
                       @SerializedName("releases_rated") var releasesRated: Int = 0,
                       @SerializedName("num_lists") var numLists: Int = 0,
                       var name: String = "",
                       @SerializedName("num_wantlist") var numWantlist: Int = 0,
                       @SerializedName("inventory_url") var inventoryUrl: String = "",
                       @SerializedName("avatar_url") var avatarUrl: String = "",
                       var uri: String = "",
                       @SerializedName("resource_url") var resourceUrl: String = "",
                       @SerializedName("buyer_rating") var buyerRating: Double = 0.0,
                       @SerializedName("buyer_rating_stars") var buyerRatingStars: Double = 0.0,
                       @SerializedName("buyer_num_ratings") var buyerNumRatings: Double = .0,
                       @SerializedName("seller_rating") var sellerRating: Double = .0,
                       @SerializedName("seller_rating_stars") var sellerRatingStars: Double = .0,
                       @SerializedName("seller_num_ratings") var sellerNumRatings: Int = 0,
                       @SerializedName("curr_abbr") var currAbbr: String = "")