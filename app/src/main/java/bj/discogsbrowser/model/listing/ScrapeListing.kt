package bj.discogsbrowser.model.listing

/**
 * Created by Josh Laird on 19/05/2017.
 */
data class ScrapeListing(val price: String, val convertedPrice: String, val mediaCondition: String,
                         val sleeveCondition: String, val sellerUrl: String, val sellerRating: String,
                         val shipsFrom: String, val marketPlaceId: String, val sellerName: String)