package bj.rxjavaexperimentation.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import bj.rxjavaexperimentation.model.listing.Listing;

/**
 * Created by Josh Laird on 12/04/2017.
 */

public class DiscogsScraper
{
    @Inject
    public DiscogsScraper()
    {

    }

    public ArrayList<Listing> scrapeListings(String id, String type) throws IOException
    {
        StringBuilder stringBuilder = new StringBuilder("http://www.discogs.com/sell/list?sort=price%2Casc&limit=50&ev=mb");
        ArrayList<Listing> listings = new ArrayList<>();
        switch (type)
        {
            case "master":
                stringBuilder.append("&master_id=").append(id);
                break;
            case "release":
                stringBuilder.append("&release_id=").append(id);
                break;
        }
        Document doc = Jsoup
                .connect(stringBuilder.toString())
                // Try and get the mobile site
                .userAgent("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>")
                .get();
        Elements marketplaceListings = doc.getElementsByClass("shortcut_navigable ");
        for (Element element : marketplaceListings)
        {
            // Price element
            String price = element.getElementsByClass("hide_desktop").get(0).getElementsByClass("price").get(0).text();
            String convertedPrice = "";
            try
            {
                convertedPrice = element.getElementsByClass("hide_desktop").get(0).getElementsByClass("converted_price").get(0).text();
            }
            catch (IndexOutOfBoundsException e)
            {
                // Listed in local price
            }
            // Contains media and sleeve condition
            Element itemDescription = element.getElementsByClass("item_description").get(0);
            String mediaCondition = itemDescription.getElementsByClass("icon icon-info-circle muted media-condition-tooltip").get(0).attr("data-condition");
            String sleeveCondition = "";
            try
            {
                sleeveCondition = itemDescription.getElementsByClass("item_sleeve_condition").get(0).text();
            }
            catch (IndexOutOfBoundsException e)
            {
                // No sleeve
            }

            Element sellerInfo = element.getElementsByClass("seller_info").get(0);
            String sellerUrl = sellerInfo.getElementsByTag("a").attr("href");
            String sellerName = sellerInfo.getElementsByTag("a").text().split(" ")[0];

            String marketPlaceId = element.getElementsByClass("button button_green cart_button ").attr("data-item-id");

            listings.add(new Listing(price, convertedPrice, mediaCondition, sleeveCondition, sellerUrl, sellerName, marketPlaceId));
        }
        return listings;
    }
}
