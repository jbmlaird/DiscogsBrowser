package bj.discogsbrowser.release

import bj.discogsbrowser.model.listing.ScrapeListing

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ScrapeListFactory {
    @JvmStatic fun buildOneEmptyScrapeListing(): List<ScrapeListing> {
        return listOf(buildScrapeListing("", "", "", "", "", "", "", ""))
    }

    @JvmStatic fun buildFourEmptyScrapeListing(): List<ScrapeListing> {
        return listOf(buildScrapeListing("1", "", "", "", "", "", "", ""),
                buildScrapeListing("2", "", "", "", "", "", "", ""),
                buildScrapeListing("3", "", "", "", "", "", "", ""),
                buildScrapeListing("4", "", "", "", "", "", "", ""))
    }

    private fun buildScrapeListing(price: String, convertedPrice: String, mediaCondition: String,
                                   sleeveCondition: String, sellerName: String,
                                   sellerRating: String, shipsFrom: String, marketPlaceId: String): ScrapeListing {
        return ScrapeListing(price, convertedPrice, mediaCondition, sleeveCondition,
                "", sellerRating, shipsFrom, marketPlaceId, sellerName)
    }
}