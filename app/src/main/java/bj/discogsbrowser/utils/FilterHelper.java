package bj.discogsbrowser.utils;

import java.util.List;

import bj.discogsbrowser.model.common.RecyclerViewModel;
import bj.discogsbrowser.model.listing.Listing;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;

/**
 * Created by Josh Laird on 08/05/2017.
 * <p>
 * Contains two {@link SingleTransformer}s to filter {@link Single}s to the given text.
 */
public class FilterHelper
{
    private String filterText = "";

    public void setFilterText(String filterText)
    {
        this.filterText = filterText;
    }

    /**
     * Filters the given list against whether their Title or Subtitle matches the filter text.
     *
     * @return Filtered list.
     */
    public SingleTransformer<List<? extends RecyclerViewModel>, List<? extends RecyclerViewModel>> filterByFilterText()
    {
        return untransformed ->
                (Single) untransformed.flattenAsObservable(items -> items)
                        .filter(item ->
                                item.getSubtitle().toLowerCase().contains(filterText) || item.getTitle().toLowerCase().contains(filterText))
                        .toList();
    }

    /**
     * Filters the list to items that are listed as For Sale.
     *
     * @return Filtered list.
     */
    public SingleTransformer<List<Listing>, List<Listing>> filterForSale()
    {
        return listingsSingle ->
                listingsSingle.flattenAsObservable(listings -> listings)
                        .filter(listing ->
                                listing.getStatus().equals("For Sale"))
                        .toList();
    }
}
