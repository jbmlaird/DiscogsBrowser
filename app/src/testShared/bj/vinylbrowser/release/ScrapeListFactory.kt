package bj.vinylbrowser.release

import bj.vinylbrowser.model.listing.ScrapeListing

/**
 * Created by Josh Laird on 19/05/2017.
 */
object ScrapeListFactory {
    @JvmStatic fun buildOneEmptyScrapeListing(): List<ScrapeListing> {
        return listOf(buildScrapeListing("", "", "", "", "", "", "", "", ""))
    }

    @JvmStatic fun buildFourEmptyScrapeListing(): List<ScrapeListing> {
        return listOf(buildScrapeListing("scrapeListingPrice1", "", "", "", "", "", "", "", "marketplaceId1"),
                buildScrapeListing("scrapeListingPrice2", "", "", "", "", "", "", "", "marketplaceId2"),
                buildScrapeListing("scrapeListingPrice3", "", "", "", "", "", "", "", "marketplaceId3"),
                buildScrapeListing("scrapeListingPrice4", "", "", "", "", "", "", "", "marketplaceId4"))
    }

    private fun buildScrapeListing(price: String, convertedPrice: String, mediaCondition: String,
                                   sleeveCondition: String, sellerUrl: String, sellerName: String, sellerRating: String,
                                   shipsFrom: String, marketPlaceId: String): ScrapeListing {
        return ScrapeListing(price, convertedPrice, mediaCondition, sleeveCondition, sellerUrl,
                sellerName, sellerRating, shipsFrom, marketPlaceId)
    }
}